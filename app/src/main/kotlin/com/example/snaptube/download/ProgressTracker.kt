package com.example.snaptube.download

import android.util.Log
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Pattern

/**
 * متتبع تقدم التحميل
 * يقوم بتحليل مخرجات yt-dlp لاستخراج معلومات التقدم
 */
class ProgressTracker {
    private val TAG = "ProgressTracker"
    
    // خريطة لتتبع تقدم التحميلات النشطة
    private val activeTrackers = ConcurrentHashMap<String, Job>()
    
    // أنماط تحليل مخرجات yt-dlp
    private val progressPattern = Pattern.compile(
        "\\[download\\]\\s+(\\d+(?:\\.\\d+)?)%\\s+of\\s+(\\S+)\\s+at\\s+(\\S+)\\s+ETA\\s+(\\S+)"
    )
    
    private val downloadingPattern = Pattern.compile(
        "\\[download\\]\\s+Downloading\\s+video\\s+(\\d+)\\s+of\\s+(\\d+)"
    )
    
    private val fileSizePattern = Pattern.compile(
        "\\[download\\]\\s+Destination:\\s+(.+)"
    )
    
    private val errorPattern = Pattern.compile(
        "ERROR:\\s+(.+)"
    )
    
    private val warningPattern = Pattern.compile(
        "WARNING:\\s+(.+)"
    )
    
    /**
     * تتبع تقدم التحميل من مخرجات العملية
     */
    fun trackProgress(
        downloadId: String,
        inputStream: InputStream,
        onProgress: (ProgressInfo) -> Unit
    ) {
        // إيقاف أي متتبع سابق لنفس التحميل
        stopTracking(downloadId)
        
        val job = CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "بدء تتبع التقدم للتحميل: $downloadId")
                
                val reader = BufferedReader(InputStreamReader(inputStream))
                var lastProgressInfo = ProgressInfo()
                
                reader.use { r ->
                    var line: String?
                    while (r.readLine().also { line = it } != null && isActive) {
                        line?.let { currentLine ->
                            val progressInfo = parseLine(currentLine, lastProgressInfo)
                            if (progressInfo != lastProgressInfo) {
                                lastProgressInfo = progressInfo
                                onProgress(progressInfo)
                            }
                        }
                    }
                }
                
                Log.d(TAG, "انتهى تتبع التقدم للتحميل: $downloadId")
                
            } catch (e: Exception) {
                Log.e(TAG, "خطأ في تتبع التقدم للتحميل: $downloadId", e)
                onProgress(
                    ProgressInfo(
                        hasError = true,
                        errorMessage = e.message ?: "خطأ غير معروف"
                    )
                )
            } finally {
                activeTrackers.remove(downloadId)
            }
        }
        
        activeTrackers[downloadId] = job
    }
    
    /**
     * إيقاف تتبع تحميل محدد
     */
    fun stopTracking(downloadId: String) {
        activeTrackers[downloadId]?.cancel()
        activeTrackers.remove(downloadId)
    }
    
    /**
     * إيقاف جميع عمليات التتبع
     */
    fun cleanup() {
        activeTrackers.values.forEach { it.cancel() }
        activeTrackers.clear()
    }
    
    /**
     * تحليل سطر واحد من مخرجات yt-dlp
     */
    private fun parseLine(line: String, lastProgress: ProgressInfo): ProgressInfo {
        try {
            // تحليل تقدم التحميل
            val progressMatcher = progressPattern.matcher(line)
            if (progressMatcher.find()) {
                return parseProgressLine(progressMatcher, lastProgress)
            }
            
            // تحليل معلومات التحميل
            val downloadingMatcher = downloadingPattern.matcher(line)
            if (downloadingMatcher.find()) {
                return parseDownloadingLine(downloadingMatcher, lastProgress)
            }
            
            // تحليل وجهة الملف
            val fileSizeMatcher = fileSizePattern.matcher(line)
            if (fileSizeMatcher.find()) {
                return parseDestinationLine(fileSizeMatcher, lastProgress)
            }
            
            // تحليل الأخطاء
            val errorMatcher = errorPattern.matcher(line)
            if (errorMatcher.find()) {
                return parseErrorLine(errorMatcher, lastProgress)
            }
            
            // تحليل التحذيرات
            val warningMatcher = warningPattern.matcher(line)
            if (warningMatcher.find()) {
                return parseWarningLine(warningMatcher, lastProgress)
            }
            
            // فحص حالات خاصة
            when {
                line.contains("[download] 100%") -> {
                    return lastProgress.copy(
                        progress = 100,
                        isCompleted = true,
                        message = "تم إكمال التحميل"
                    )
                }
                
                line.contains("[ffmpeg]") -> {
                    return lastProgress.copy(
                        isProcessing = true,
                        message = "جاري معالجة الفيديو..."
                    )
                }
                
                line.contains("Merging formats") -> {
                    return lastProgress.copy(
                        isProcessing = true,
                        message = "جاري دمج تنسيقات الصوت والفيديو..."
                    )
                }
                
                line.contains("Deleting original file") -> {
                    return lastProgress.copy(
                        isProcessing = true,
                        message = "جاري حذف الملفات المؤقتة..."
                    )
                }
            }
            
            return lastProgress
            
        } catch (e: Exception) {
            Log.w(TAG, "خطأ في تحليل السطر: $line", e)
            return lastProgress
        }
    }
    
    /**
     * تحليل سطر تقدم التحميل
     */
    private fun parseProgressLine(matcher: java.util.regex.Matcher, lastProgress: ProgressInfo): ProgressInfo {
        try {
            val progress = matcher.group(1)?.toDoubleOrNull()?.toInt() ?: 0
            val totalSize = matcher.group(2) ?: ""
            val speed = matcher.group(3) ?: ""
            val eta = matcher.group(4) ?: ""
            
            return lastProgress.copy(
                progress = progress,
                totalSizeText = totalSize,
                speedText = speed,
                etaText = eta,
                totalBytes = parseSizeToBytes(totalSize),
                speedBytes = parseSpeedToBytes(speed),
                etaSeconds = parseEtaToSeconds(eta),
                message = "جاري التحميل..."
            )
            
        } catch (e: Exception) {
            Log.w(TAG, "خطأ في تحليل سطر التقدم", e)
            return lastProgress
        }
    }
    
    /**
     * تحليل سطر معلومات التحميل
     */
    private fun parseDownloadingLine(matcher: java.util.regex.Matcher, lastProgress: ProgressInfo): ProgressInfo {
        try {
            val current = matcher.group(1)?.toIntOrNull() ?: 0
            val total = matcher.group(2)?.toIntOrNull() ?: 0
            
            return lastProgress.copy(
                currentVideo = current,
                totalVideos = total,
                message = "تحميل الفيديو $current من $total"
            )
            
        } catch (e: Exception) {
            Log.w(TAG, "خطأ في تحليل سطر التحميل", e)
            return lastProgress
        }
    }
    
    /**
     * تحليل سطر وجهة الملف
     */
    private fun parseDestinationLine(matcher: java.util.regex.Matcher, lastProgress: ProgressInfo): ProgressInfo {
        try {
            val destination = matcher.group(1) ?: ""
            
            return lastProgress.copy(
                destination = destination,
                message = "جاري التحميل إلى: ${getFileNameFromPath(destination)}"
            )
            
        } catch (e: Exception) {
            Log.w(TAG, "خطأ في تحليل سطر الوجهة", e)
            return lastProgress
        }
    }
    
    /**
     * تحليل سطر الخطأ
     */
    private fun parseErrorLine(matcher: java.util.regex.Matcher, lastProgress: ProgressInfo): ProgressInfo {
        try {
            val errorMessage = matcher.group(1) ?: "خطأ غير معروف"
            
            return lastProgress.copy(
                hasError = true,
                errorMessage = errorMessage,
                message = "خطأ: $errorMessage"
            )
            
        } catch (e: Exception) {
            Log.w(TAG, "خطأ في تحليل سطر الخطأ", e)
            return lastProgress
        }
    }
    
    /**
     * تحليل سطر التحذير
     */
    private fun parseWarningLine(matcher: java.util.regex.Matcher, lastProgress: ProgressInfo): ProgressInfo {
        try {
            val warningMessage = matcher.group(1) ?: ""
            
            return lastProgress.copy(
                hasWarning = true,
                warningMessage = warningMessage,
                message = "تحذير: $warningMessage"
            )
            
        } catch (e: Exception) {
            Log.w(TAG, "خطأ في تحليل سطر التحذير", e)
            return lastProgress
        }
    }
    
    /**
     * تحويل حجم الملف النصي إلى بايت
     */
    private fun parseSizeToBytes(sizeText: String): Long {
        try {
            val pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)(\\w+)")
            val matcher = pattern.matcher(sizeText)
            
            if (matcher.find()) {
                val value = matcher.group(1)?.toDoubleOrNull() ?: 0.0
                val unit = matcher.group(2)?.uppercase() ?: ""
                
                return when (unit) {
                    "B" -> value.toLong()
                    "KB", "KIB" -> (value * 1024).toLong()
                    "MB", "MIB" -> (value * 1024 * 1024).toLong()
                    "GB", "GIB" -> (value * 1024 * 1024 * 1024).toLong()
                    else -> 0L
                }
            }
            
            return 0L
            
        } catch (e: Exception) {
            Log.w(TAG, "خطأ في تحليل حجم الملف: $sizeText", e)
            return 0L
        }
    }
    
    /**
     * تحويل سرعة التحميل النصية إلى بايت/ثانية
     */
    private fun parseSpeedToBytes(speedText: String): Long {
        try {
            val pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)(\\w+)/s")
            val matcher = pattern.matcher(speedText)
            
            if (matcher.find()) {
                val value = matcher.group(1)?.toDoubleOrNull() ?: 0.0
                val unit = matcher.group(2)?.uppercase() ?: ""
                
                return when (unit) {
                    "B" -> value.toLong()
                    "KB", "KIB" -> (value * 1024).toLong()
                    "MB", "MIB" -> (value * 1024 * 1024).toLong()
                    "GB", "GIB" -> (value * 1024 * 1024 * 1024).toLong()
                    else -> 0L
                }
            }
            
            return 0L
            
        } catch (e: Exception) {
            Log.w(TAG, "خطأ في تحليل السرعة: $speedText", e)
            return 0L
        }
    }
    
    /**
     * تحويل الوقت المتبقي النصي إلى ثوان
     */
    private fun parseEtaToSeconds(etaText: String): Long {
        try {
            // نعيد نتيجة التحويل عبر when
            return when {
                etaText.contains(":") -> {
                    // تنسيق HH:MM:SS أو MM:SS
                    val parts = etaText.split(":")
                    when (parts.size) {
                        2 -> { // MM:SS
                            val minutes = parts[0].toLongOrNull() ?: 0
                            val seconds = parts[1].toLongOrNull() ?: 0
                            minutes * 60 + seconds
                        }
                        3 -> { // HH:MM:SS
                            val hours = parts[0].toLongOrNull() ?: 0
                            val minutes = parts[1].toLongOrNull() ?: 0
                            val seconds = parts[2].toLongOrNull() ?: 0
                            hours * 3600 + minutes * 60 + seconds
                        }
                        else -> 0L
                    }
                }
                etaText.endsWith("s") -> {
                    etaText.dropLast(1).toLongOrNull() ?: 0L
                }
                etaText.endsWith("m") -> {
                    (etaText.dropLast(1).toLongOrNull() ?: 0L) * 60
                }
                etaText.endsWith("h") -> {
                    (etaText.dropLast(1).toLongOrNull() ?: 0L) * 3600
                }
                else -> {
                    etaText.toLongOrNull() ?: 0L
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "خطأ في تحليل الوقت المتبقي: $etaText", e)
            return 0L
        }
    }
    
    /**
     * استخراج اسم الملف من المسار
     */
    private fun getFileNameFromPath(path: String): String {
        return path.substringAfterLast("/").substringAfterLast("\\")
    }
}

/**
 * معلومات تقدم التحميل
 */
data class ProgressInfo(
    val progress: Int = 0,
    val totalBytes: Long = 0,
    val downloadedBytes: Long = 0,
    val speedBytes: Long = 0,
    val etaSeconds: Long = 0,
    val totalSizeText: String = "",
    val speedText: String = "",
    val etaText: String = "",
    val currentVideo: Int = 0,
    val totalVideos: Int = 0,
    val destination: String = "",
    val message: String = "",
    val isCompleted: Boolean = false,
    val isProcessing: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String = "",
    val hasWarning: Boolean = false,
    val warningMessage: String = ""
) {
    
    /**
     * حساب البايت المحملة من النسبة المئوية والحجم الإجمالي
     */
    fun calculateDownloadedBytes(): Long {
        return if (totalBytes > 0 && progress > 0) {
            (totalBytes * progress) / 100
        } else {
            downloadedBytes
        }
    }
    
    /**
     * تحقق من صحة البيانات
     */
    fun isValid(): Boolean {
        return progress in 0..100 && !hasError
    }
    
    /**
     * حصول على رسالة مختصرة للحالة
     */
    fun getShortMessage(): String {
        return when {
            hasError -> "خطأ"
            isCompleted -> "مكتمل"
            isProcessing -> "معالجة"
            progress > 0 -> "$progress%"
            else -> "جاري البدء"
        }
    }
}
