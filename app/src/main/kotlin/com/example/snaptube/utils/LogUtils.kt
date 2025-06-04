package com.example.snaptube.utils

import android.content.Context
import android.os.Build
import android.util.Log
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import kotlinx.coroutines.*

/**
 * Advanced logging utility for Snaptube with file logging, log rotation, and filtering
 * Provides comprehensive logging capabilities with performance optimization
 */
class LogUtils private constructor(private val context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: LogUtils? = null
        
        private const val LOG_FILE_NAME = "snaptube_logs.txt"
        private const val MAX_LOG_FILE_SIZE = 5 * 1024 * 1024 // 5MB
        private const val MAX_LOG_FILES = 5
        private const val LOG_BUFFER_SIZE = 100
        private const val LOG_FLUSH_INTERVAL = 30_000L // 30 seconds
        
        private val LOG_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
        private val FILE_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US)
        
        fun getInstance(context: Context): LogUtils {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LogUtils(context.applicationContext).also { INSTANCE = it }
            }
        }
        
        // Convenience methods for quick logging
        fun d(tag: String, message: String) = Timber.tag(tag).d(message)
        fun i(tag: String, message: String) = Timber.tag(tag).i(message)
        fun w(tag: String, message: String) = Timber.tag(tag).w(message)
        fun e(tag: String, message: String, throwable: Throwable? = null) {
            if (throwable != null) {
                Timber.tag(tag).e(throwable, message)
            } else {
                Timber.tag(tag).e(message)
            }
        }
    }

    enum class LogLevel(val priority: Int, val symbol: String) {
        VERBOSE(Log.VERBOSE, "V"),
        DEBUG(Log.DEBUG, "D"),
        INFO(Log.INFO, "I"),
        WARN(Log.WARN, "W"),
        ERROR(Log.ERROR, "E"),
        ASSERT(Log.ASSERT, "A")
    }

    data class LogEntry(
        val timestamp: Long,
        val level: LogLevel,
        val tag: String,
        val message: String,
        val throwable: Throwable? = null,
        val threadName: String = Thread.currentThread().name
    ) {
        fun formatForFile(): String {
            val date = LOG_DATE_FORMAT.format(Date(timestamp))
            val throwableStr = throwable?.let { "\n${Log.getStackTraceString(it)}" } ?: ""
            return "$date ${level.symbol}/$tag [$threadName]: $message$throwableStr"
        }
        
        fun formatForConsole(): String {
            return "${level.symbol}/$tag: $message"
        }
    }

    private val logDir: File by lazy {
        File(context.getExternalFilesDir(null), "logs").apply {
            if (!exists()) mkdirs()
        }
    }
    
    private val currentLogFile: File by lazy {
        File(logDir, LOG_FILE_NAME)
    }
    
    private val logBuffer = ConcurrentLinkedQueue<LogEntry>()
    private val logScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    private var isFileLoggingEnabled = true
    private var currentLogLevel = LogLevel.DEBUG
    private var maxFileSize = MAX_LOG_FILE_SIZE
    private var maxFiles = MAX_LOG_FILES
    
    init {
        // Start background log flushing
        startLogFlushing()
        
        // Clean up old log files on startup
        cleanupOldLogs()
        
        // Set up Timber
        setupTimber()
    }

    /**
     * Setup Timber with custom tree for file logging
     */
    private fun setupTimber() {
        if (Timber.forest().isEmpty()) {
            Timber.plant(SnaptubeLogTree())
        }
    }

    /**
     * Custom Timber tree that integrates with our logging system
     */
    inner class SnaptubeLogTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            val logLevel = LogLevel.values().find { it.priority == priority } ?: LogLevel.DEBUG
            val logTag = tag ?: "Snaptube"
            
            addLogEntry(LogEntry(
                timestamp = System.currentTimeMillis(),
                level = logLevel,
                tag = logTag,
                message = message,
                throwable = t
            ))
        }
    }

    /**
     * Add a log entry to the buffer
     */
    private fun addLogEntry(entry: LogEntry) {
        // Check if log level should be recorded
        if (entry.level.priority < currentLogLevel.priority) {
            return
        }
        
        // Add to buffer
        logBuffer.offer(entry)
        
        // Flush if buffer is full
        if (logBuffer.size >= LOG_BUFFER_SIZE) {
            flushLogsAsync()
        }
    }

    /**
     * Log debug message
     */
    fun debug(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }

    /**
     * Log info message
     */
    fun info(tag: String, message: String) {
        Timber.tag(tag).i(message)
    }

    /**
     * Log warning message
     */
    fun warn(tag: String, message: String) {
        Timber.tag(tag).w(message)
    }

    /**
     * Log error message
     */
    fun error(tag: String, message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Timber.tag(tag).e(throwable, message)
        } else {
            Timber.tag(tag).e(message)
        }
    }

    /**
     * Log download progress
     */
    fun logDownloadProgress(downloadId: String, progress: Int, speed: String) {
        debug("DownloadProgress", "[$downloadId] Progress: $progress%, Speed: $speed")
    }

    /**
     * Log video extraction
     */
    fun logVideoExtraction(url: String, duration: Long, success: Boolean) {
        if (success) {
            info("VideoExtraction", "Successfully extracted info for $url in ${duration}ms")
        } else {
            error("VideoExtraction", "Failed to extract info for $url after ${duration}ms")
        }
    }

    /**
     * Log network operations
     */
    fun logNetworkOperation(operation: String, url: String, duration: Long, success: Boolean) {
        val message = "[$operation] $url - ${duration}ms"
        if (success) {
            debug("Network", message + " (SUCCESS)")
        } else {
            warn("Network", message + " (FAILED)")
        }
    }

    /**
     * Log database operations
     */
    fun logDatabaseOperation(operation: String, table: String, rowCount: Int, duration: Long) {
        debug("Database", "[$operation] $table - $rowCount rows in ${duration}ms")
    }

    /**
     * Log file operations
     */
    fun logFileOperation(operation: String, filePath: String, size: Long? = null, success: Boolean) {
        val sizeStr = size?.let { " (${formatFileSize(it)})" } ?: ""
        val status = if (success) "SUCCESS" else "FAILED"
        info("FileOperation", "[$operation] $filePath$sizeStr - $status")
    }

    /**
     * Log app lifecycle events
     */
    fun logAppLifecycle(event: String, additionalInfo: String = "") {
        val info = if (additionalInfo.isNotEmpty()) " - $additionalInfo" else ""
        info("AppLifecycle", "$event$info")
    }

    /**
     * Start background log flushing
     */
    private fun startLogFlushing() {
        logScope.launch {
            while (true) {
                delay(LOG_FLUSH_INTERVAL)
                flushLogs()
            }
        }
    }

    /**
     * Flush logs asynchronously
     */
    private fun flushLogsAsync() {
        logScope.launch {
            flushLogs()
        }
    }

    /**
     * Flush logs to file
     */
    private suspend fun flushLogs() {
        if (!isFileLoggingEnabled || logBuffer.isEmpty()) {
            return
        }
        
        try {
            val entries = mutableListOf<LogEntry>()
            while (logBuffer.isNotEmpty() && entries.size < LOG_BUFFER_SIZE) {
                logBuffer.poll()?.let { entries.add(it) }
            }
            
            if (entries.isNotEmpty()) {
                writeLogsToFile(entries)
            }
        } catch (e: Exception) {
            // Don't use Timber here to avoid infinite loop
            Log.e("LogUtils", "Failed to flush logs", e)
        }
    }

    /**
     * Write logs to file
     */
    private suspend fun writeLogsToFile(entries: List<LogEntry>) {
        withContext(Dispatchers.IO) {
            try {
                // Check if log rotation is needed
                if (currentLogFile.exists() && currentLogFile.length() > maxFileSize) {
                    rotateLogFiles()
                }
                
                // Write entries to file
                FileWriter(currentLogFile, true).use { writer ->
                    entries.forEach { entry ->
                        writer.appendLine(entry.formatForFile())
                    }
                    writer.flush()
                }
            } catch (e: IOException) {
                Log.e("LogUtils", "Failed to write logs to file", e)
            }
        }
    }

    /**
     * Rotate log files when they get too large
     */
    private fun rotateLogFiles() {
        try {
            // Rename current log file with timestamp
            val timestamp = FILE_DATE_FORMAT.format(Date())
            val archivedFile = File(logDir, "snaptube_logs_$timestamp.txt")
            
            if (currentLogFile.renameTo(archivedFile)) {
                Log.d("LogUtils", "Rotated log file to: ${archivedFile.name}")
            }
            
            // Clean up old files
            cleanupOldLogs()
        } catch (e: Exception) {
            Log.e("LogUtils", "Failed to rotate log files", e)
        }
    }

    /**
     * Clean up old log files
     */
    private fun cleanupOldLogs() {
        try {
            val logFiles = logDir.listFiles { file ->
                file.name.startsWith("snaptube_logs") && file.name.endsWith(".txt")
            }?.sortedByDescending { it.lastModified() }
            
            logFiles?.drop(maxFiles)?.forEach { file ->
                if (file.delete()) {
                    Log.d("LogUtils", "Deleted old log file: ${file.name}")
                }
            }
        } catch (e: Exception) {
            Log.e("LogUtils", "Failed to cleanup old logs", e)
        }
    }

    /**
     * Get all log files
     */
    fun getLogFiles(): List<File> {
        return try {
            logDir.listFiles { file ->
                file.name.startsWith("snaptube_logs") && file.name.endsWith(".txt")
            }?.sortedByDescending { it.lastModified() }?.toList() ?: emptyList()
        } catch (e: Exception) {
            Log.e("LogUtils", "Failed to get log files", e)
            emptyList()
        }
    }

    /**
     * Get current log file content
     */
    fun getCurrentLogContent(): String {
        return try {
            if (currentLogFile.exists()) {
                currentLogFile.readText()
            } else {
                "No log file found"
            }
        } catch (e: Exception) {
            Log.e("LogUtils", "Failed to read log file", e)
            "Error reading log file: ${e.message}"
        }
    }

    /**
     * Clear all log files
     */
    fun clearAllLogs() {
        try {
            // Flush any pending logs first
            runBlocking {
                flushLogs()
            }
            
            // Delete all log files
            getLogFiles().forEach { file ->
                if (file.delete()) {
                    Log.d("LogUtils", "Deleted log file: ${file.name}")
                }
            }
            
            Log.i("LogUtils", "All log files cleared")
        } catch (e: Exception) {
            Log.e("LogUtils", "Failed to clear log files", e)
        }
    }

    /**
     * Export logs as a single file
     */
    suspend fun exportLogs(): File? {
        return withContext(Dispatchers.IO) {
            try {
                // Flush any pending logs
                flushLogs()
                
                val timestamp = FILE_DATE_FORMAT.format(Date())
                val exportFile = File(logDir, "snaptube_logs_export_$timestamp.txt")
                
                FileWriter(exportFile).use { writer ->
                    // Write device and app info
                    writer.appendLine("=== SNAPTUBE LOG EXPORT ===")
                    writer.appendLine("Export Date: ${LOG_DATE_FORMAT.format(Date())}")
                    writer.appendLine("Device: ${Build.MANUFACTURER} ${Build.MODEL}")
                    writer.appendLine("Android Version: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")
                    writer.appendLine("App Version: ${getAppVersion()}")
                    writer.appendLine("================================\n")
                    
                    // Write all log files in chronological order
                    getLogFiles().reversed().forEach { logFile ->
                        writer.appendLine("=== ${logFile.name} ===")
                        writer.appendLine(logFile.readText())
                        writer.appendLine("\n")
                    }
                }
                
                Log.i("LogUtils", "Exported logs to: ${exportFile.name}")
                exportFile
            } catch (e: Exception) {
                Log.e("LogUtils", "Failed to export logs", e)
                null
            }
        }
    }

    /**
     * Get app version for logs
     */
    private fun getAppVersion(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            "${packageInfo.versionName} (${packageInfo.longVersionCode})"
        } catch (e: Exception) {
            "Unknown"
        }
    }

    /**
     * Format file size for display
     */
    private fun formatFileSize(bytes: Long): String {
        val units = arrayOf("B", "KB", "MB", "GB")
        var size = bytes.toDouble()
        var unitIndex = 0
        
        while (size >= 1024 && unitIndex < units.size - 1) {
            size /= 1024
            unitIndex++
        }
        
        return "%.1f %s".format(size, units[unitIndex])
    }

    /**
     * Configure logging settings
     */
    fun configure(
        enableFileLogging: Boolean = true,
        logLevel: LogLevel = LogLevel.DEBUG,
        maxFileSize: Int = MAX_LOG_FILE_SIZE,
        maxFiles: Int = MAX_LOG_FILES
    ) {
        this.isFileLoggingEnabled = enableFileLogging
        this.currentLogLevel = logLevel
        this.maxFileSize = maxFileSize
        this.maxFiles = maxFiles
        
        Log.i("LogUtils", "Configuration updated: fileLogging=$enableFileLogging, level=$logLevel")
    }

    /**
     * Get logging statistics
     */
    fun getLogStats(): Map<String, Any> {
        val logFiles = getLogFiles()
        val totalSize = logFiles.sumOf { it.length() }
        
        return mapOf(
            "fileLoggingEnabled" to isFileLoggingEnabled,
            "currentLogLevel" to currentLogLevel.name,
            "logFileCount" to logFiles.size,
            "totalLogSize" to formatFileSize(totalSize),
            "bufferSize" to logBuffer.size,
            "currentLogFileSize" to formatFileSize(currentLogFile.length()),
            "maxFileSize" to formatFileSize(maxFileSize.toLong()),
            "maxFiles" to maxFiles
        )
    }

    /**
     * Clean up resources
     */
    fun cleanup() {
        logScope.cancel()
        runBlocking {
            flushLogs()
        }
    }
}
