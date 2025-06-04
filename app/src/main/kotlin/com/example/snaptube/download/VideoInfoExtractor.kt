package com.example.snaptube.download

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import com.example.snaptube.models.*
import org.json.JSONObject
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.regex.Pattern
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest
import com.yausername.youtubedl_android.YoutubeDLResponse

/**
 * مستخرج معلومات الفيديو باستخدام yt-dlp
 * مسؤول عن استخراج جميع المعلومات المتعلقة بالفيديو من المنصات المختلفة
 */
class VideoInfoExtractor(
    private val context: Context
) {
    private val TAG = "VideoInfoExtractor"
    
    // أنماط الروابط المدعومة
    private val supportedPlatforms = mapOf(
        "youtube" to listOf(
            Pattern.compile("(?:https?://)?(?:www\\.)?(youtube\\.com|youtu\\.be)"),
            Pattern.compile("(?:https?://)?(?:www\\.)?youtube\\.com/watch\\?v=([a-zA-Z0-9_-]+)"),
            Pattern.compile("(?:https?://)?youtu\\.be/([a-zA-Z0-9_-]+)")
        ),
        "twitter" to listOf(
            Pattern.compile("(?:https?://)?(?:www\\.)?(twitter\\.com|x\\.com)"),
        ),
        "instagram" to listOf(
            Pattern.compile("(?:https?://)?(?:www\\.)?instagram\\.com")
        ),
        "tiktok" to listOf(
            Pattern.compile("(?:https?://)?(?:www\\.)?tiktok\\.com")
        ),
        "facebook" to listOf(
            Pattern.compile("(?:https?://)?(?:www\\.)?facebook\\.com")
        )
    )
    
    /**
     * استخراج معلومات الفيديو من الرابط
     */
    suspend fun extractVideoInfo(url: String): VideoInfo {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "استخراج معلومات الفيديو من: $url")
                
                // التحقق من صحة الرابط
                if (!isUrlSupported(url)) {
                    throw IllegalArgumentException("الرابط غير مدعوم")
                }
                
                // تشغيل yt-dlp لاستخراج المعلومات
                val videoInfo = extractWithYtDlp(url)
                
                Log.d(TAG, "تم استخراج معلومات الفيديو بنجاح: ${videoInfo.title}")
                videoInfo
                
            } catch (e: Exception) {
                Log.e(TAG, "خطأ في استخراج معلومات الفيديو", e)
                throw e
            }
        }
    }
    
    /**
     * استخراج معلومات قائمة التشغيل
     */
    suspend fun extractPlaylistInfo(url: String): PlaylistInfo {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "استخراج معلومات قائمة التشغيل من: $url")
                
                // إنشاء YoutubeDL request للـ playlist
                val request = YoutubeDLRequest(url).apply {
                    addOption("--dump-json")
                    addOption("--flat-playlist")
                    addOption("--ignore-errors")
                }
                
                val response = YoutubeDL.getInstance().execute(request)
                
                if (response.exitCode != 0) {
                    throw RuntimeException("فشل في استخراج معلومات قائمة التشغيل: ${response.err}")
                }
                
                parsePlaylistInfo(response.out)
                
            } catch (e: Exception) {
                Log.e(TAG, "خطأ في استخراج معلومات قائمة التشغيل", e)
                throw e
            }
        }
    }
    
    /**
     * الحصول على تنسيقات التحميل المتاحة
     */
    suspend fun getAvailableFormats(url: String): List<DownloadFormat> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "جلب التنسيقات المتاحة لـ: $url")
                
                // إنشاء YoutubeDL request للحصول على JSON مع formats
                val request = YoutubeDLRequest(url).apply {
                    addOption("--dump-json")
                    addOption("--no-download")
                    addOption("--no-playlist")
                    addOption("--no-warnings")
                    // إضافة User-Agent لحل مشكلة HTTP 403
                    addOption("--user-agent", "Mozilla/5.0 (Linux; Android 10; SM-G973F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36")
                    // تعطيل التحقق من SSL
                    addOption("--no-check-certificates")
                    // استخدام IPv4 فقط
                    addOption("--force-ipv4")
                    // إعادة المحاولة
                    addOption("--retries", "3")
                    // timeout
                    addOption("--socket-timeout", "30")
                }
                
                val response = YoutubeDL.getInstance().execute(request)
                
                if (response.exitCode != 0) {
                    Log.w(TAG, "فشل في جلب التنسيقات، استخدام تنسيقات افتراضية")
                    Log.w(TAG, "خطأ YoutubeDL: ${response.err}")
                    return@withContext getDefaultFormats()
                }
                
                // طباعة الناتج للتشخيص
                Log.d(TAG, "ناتج YoutubeDL للتنسيقات:")
                Log.d(TAG, "طول الناتج: ${response.out.length} حرف")
                Log.d(TAG, "أول 500 حرف: ${response.out.take(500)}")
                
                // تحليل JSON response للحصول على formats
                val formats = parseFormatsFromJson(response.out)
                if (formats.isNotEmpty()) {
                    Log.d(TAG, "تم جلب ${formats.size} تنسيق بنجاح")
                    formats.forEach { format ->
                        Log.d(TAG, "تنسيق: ${format.formatId} - ${format.quality} - ${format.extension}")
                    }
                    formats
                } else {
                    Log.w(TAG, "لم يتم العثور على تنسيقات في JSON، استخدام افتراضية")
                    getDefaultFormats()
                }
                
            } catch (e: Exception) {
                Log.e(TAG, "خطأ في جلب التنسيقات", e)
                getDefaultFormats()
            }
        }
    }
    
    /**
     * الحصول على تنسيقات افتراضية في حالة الفشل
     */
    private fun getDefaultFormats(): List<DownloadFormat> {
        Log.d(TAG, "استخدام التنسيقات الافتراضية")
        return listOf(
            // تنسيقات فيديو عالية الجودة
            DownloadFormat(
                formatId = "best",
                extension = "mp4",
                quality = "أفضل جودة",
                fileSize = null,
                hasVideo = true,
                hasAudio = true,
                vcodec = "avc1",
                acodec = "mp4a",
                fps = 30,
                width = 1920,
                height = 1080,
                audioBitrate = 128
            ),
            DownloadFormat(
                formatId = "720p",
                extension = "mp4", 
                quality = "720p",
                fileSize = null,
                hasVideo = true,
                hasAudio = true,
                vcodec = "avc1",
                acodec = "mp4a",
                fps = 30,
                width = 1280,
                height = 720,
                audioBitrate = 128
            ),
            DownloadFormat(
                formatId = "480p",
                extension = "mp4", 
                quality = "480p",
                fileSize = null,
                hasVideo = true,
                hasAudio = true,
                vcodec = "avc1",
                acodec = "mp4a",
                fps = 30,
                width = 854,
                height = 480,
                audioBitrate = 96
            ),
            DownloadFormat(
                formatId = "360p",
                extension = "mp4", 
                quality = "360p",
                fileSize = null,
                hasVideo = true,
                hasAudio = true,
                vcodec = "avc1",
                acodec = "mp4a",
                fps = 30,
                width = 640,
                height = 360,
                audioBitrate = 64
            ),
            // تنسيقات صوت فقط
            DownloadFormat(
                formatId = "bestaudio",
                extension = "m4a",
                quality = "صوت عالي الجودة",
                fileSize = null,
                hasVideo = false,
                hasAudio = true,
                vcodec = null,
                acodec = "mp4a",
                fps = null,
                width = null,
                height = null,
                audioBitrate = 128
            ),
            DownloadFormat(
                formatId = "audio_96k",
                extension = "m4a",
                quality = "صوت متوسط",
                fileSize = null,
                hasVideo = false,
                hasAudio = true,
                vcodec = null,
                acodec = "mp4a",
                fps = null,
                width = null,
                height = null,
                audioBitrate = 96
            )
        )
    }
    
    /**
     * الحصول على خيارات الجودة المتاحة
     */
    suspend fun getQualityOptions(url: String): List<QualityOption> {
        return withContext(Dispatchers.IO) {
            try {
                val formats = getAvailableFormats(url)
                
                // تجميع الخيارات حسب الجودة
                val qualityOptions = mutableListOf<QualityOption>()
                
                // خيارات الفيديو
                val videoQualities = formats
                    .filter { it.hasVideo }
                    .groupBy { it.height }
                    .map { (height, formats) ->
                        QualityOption(
                            id = "video_${height}p",
                            name = "${height}p",
                            quality = height ?: 0,
                            fileSize = formats.minOfOrNull { it.fileSize ?: 0L } ?: 0L,
                            isAudioOnly = false,
                            format = formats.first()
                        )
                    }
                    .sortedByDescending { it.quality }
                
                qualityOptions.addAll(videoQualities)
                
                // خيارات الصوت فقط
                val audioQualities = formats
                    .filter { it.hasAudio && !it.hasVideo }
                    .groupBy { it.audioBitrate }
                    .map { (bitrate, formats) ->
                        QualityOption(
                            id = "audio_${bitrate}kbps",
                            name = "صوت ${bitrate}kbps",
                            quality = bitrate ?: 0,
                            fileSize = formats.minOfOrNull { it.fileSize ?: 0L } ?: 0L,
                            isAudioOnly = true,
                            format = formats.first()
                        )
                    }
                    .sortedByDescending { it.quality }
                
                qualityOptions.addAll(audioQualities)
                
                qualityOptions
                
            } catch (e: Exception) {
                Log.e(TAG, "خطأ في جلب خيارات الجودة", e)
                emptyList()
            }
        }
    }
    
    /**
     * فحص ما إذا كان الرابط مدعوماً
     */
    fun isUrlSupported(url: String): Boolean {
        return supportedPlatforms.values.flatten().any { pattern ->
            pattern.matcher(url).find()
        }
    }
    
    /**
     * تحديد نوع المنصة من الرابط
     * يعيد قيمة غير قابلة للـnull أو "unknown" إذا لم يكن مدعوماً
     */
    fun detectPlatform(url: String): String {
        return supportedPlatforms.entries
            .firstOrNull { (_, patterns) -> patterns.any { pattern -> pattern.matcher(url).find() } }
            ?.key ?: "unknown"
    }
    
    /**
     * استخراج معلومات الفيديو باستخدام yt-dlp
     */
    private suspend fun extractWithYtDlp(url: String): VideoInfo {
        return withContext(Dispatchers.IO) {
            try {
                // إنشاء YoutubeDL request مع إعدادات محسنة
                val request = YoutubeDLRequest(url).apply {
                    addOption("--dump-json")
                    addOption("--no-download") 
                    addOption("--no-playlist")
                    addOption("--ignore-errors")
                    // إضافة User-Agent لحل مشكلة HTTP 403
                    addOption("--user-agent", "Mozilla/5.0 (Linux; Android 10; SM-G973F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36")
                    // تعطيل التحقق من SSL للمواقع التي لديها مشاكل شهادات
                    addOption("--no-check-certificates")
                    // استخدام IPv4 فقط لتجنب مشاكل IPv6
                    addOption("--force-ipv4")
                    // إعادة المحاولة في حالة الفشل
                    addOption("--retries", "3")
                    // تحديد timeout
                    addOption("--socket-timeout", "30")
                    // إعدادات خاصة بالمنصات
                    when (detectPlatform(url)) {
                        "youtube" -> {
                            // حل مشاكل YouTube المحددة
                            addOption("--youtube-skip-dash-manifest")
                        }
                        "instagram" -> {
                            // إعدادات Instagram محددة
                            addOption("--http-chunk-size", "10M")
                        }
                        "tiktok" -> {
                            // إعدادات TikTok محددة
                            addOption("--http-chunk-size", "5M")
                        }
                    }
                }
                
                Log.d(TAG, "تنفيذ YoutubeDL request للرابط: $url")
                
                // تنفيذ الطلب
                val response = YoutubeDL.getInstance().execute(request)
                
                if (response.exitCode != 0) {
                    Log.e(TAG, "YoutubeDL خطأ: ${response.err}")
                    throw RuntimeException("فشل في استخراج معلومات الفيديو: ${response.err}")
                }
                
                Log.d(TAG, "YoutubeDL نجح، طول الناتج: ${response.out.length}")
                
                // تحليل النتيجة
                parseVideoInfo(response.out, url)
                
            } catch (e: Exception) {
                Log.e(TAG, "خطأ في استخراج معلومات الفيديو باستخدام YoutubeDL", e)
                // fallback إلى طريقة بديلة
                extractVideoInfoFallback(url)
            }
        }
    }

    /**
     * الحصول على الـ base URL للموقع
     */
    private fun getBaseUrl(url: String): String {
        return try {
            val urlObj = URL(url)
            "${urlObj.protocol}://${urlObj.host}"
        } catch (e: Exception) {
            url
        }
    }

    /**
     * استخراج معلومات قائمة التشغيل باستخدام yt-dlp
     */
    private suspend fun extractPlaylistWithYtDlp(url: String): PlaylistInfo {
        return withContext(Dispatchers.IO) {
            try {
                // إنشاء YoutubeDL request للـ playlist
                val request = YoutubeDLRequest(url).apply {
                    addOption("--dump-json")
                    addOption("--flat-playlist")
                    addOption("--ignore-errors")
                    // إضافة User-Agent لحل مشكلة HTTP 403
                    addOption("--user-agent", "Mozilla/5.0 (Linux; Android 10; SM-G973F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36")
                    // إضافة Referer للمواقع التي تتطلبه
                    addOption("--add-header", "Referer:${getBaseUrl(url)}")
                    // استخدام cookies إذا كانت متاحة
                    addOption("--cookies-from-browser", "chrome")
                    // تعطيل التحقق من SSL للمواقع التي لديها مشاكل شهادات
                    addOption("--no-check-certificates")
                    // استخدام IPv4 فقط لتجنب مشاكل IPv6
                    addOption("--force-ipv4")
                    // إعادة المحاولة في حالة الفشل
                    addOption("--retries", "3")
                    // تحديد timeout
                    addOption("--socket-timeout", "30")
                }
                
                val response = YoutubeDL.getInstance().execute(request)
                
                if (response.exitCode != 0) {
                    throw RuntimeException("فشل في استخراج معلومات قائمة التشغيل: ${response.err}")
                }
                
                parsePlaylistInfo(response.out)
                
            } catch (e: Exception) {
                Log.e(TAG, "خطأ في استخراج معلومات قائمة التشغيل باستخدام yt-dlp", e)
                throw e
            }
        }
    }
    
    /**
     * استخراج معلومات الفيديو كـ fallback عندما يفشل YoutubeDL
     */
    private suspend fun extractVideoInfoFallback(url: String): VideoInfo {
        return withContext(Dispatchers.IO) {
            try {
                when (detectPlatform(url)) {
                    "youtube" -> extractYouTubeInfoFallback(url)
                    else -> createBasicVideoInfo(url)
                }
            } catch (e: Exception) {
                Log.e(TAG, "فشل في fallback extraction", e)
                createBasicVideoInfo(url)
            }
        }
    }
    
    /**
     * استخراج معلومات YouTube باستخدام oEmbed API
     */
    private suspend fun extractYouTubeInfoFallback(url: String): VideoInfo {
        return withContext(Dispatchers.IO) {
            try {
                // استخراج video ID من YouTube URL
                val videoId = extractYouTubeVideoId(url) ?: throw IllegalArgumentException("Invalid YouTube URL")
                
                // استخدام YouTube oEmbed API
                val oembedUrl = "https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=$videoId&format=json"
                val connection = URL(oembedUrl).openConnection()
                connection.setRequestProperty("User-Agent", "Snaptube/1.0")
                
                val response = connection.getInputStream().bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                
                VideoInfo(
                    id = videoId,
                    title = json.optString("title", "فيديو YouTube"),
                    description = "",
                    duration = 0L,
                    uploadDate = "",
                    uploader = json.optString("author_name", ""),
                    uploaderUrl = json.optString("author_url", ""),
                    viewCount = 0L,
                    likeCount = 0L,
                    thumbnail = json.optString("thumbnail_url", ""),
                    webpage_url = url,
                    originalUrl = url,
                    platform = "youtube",
                    fileSize = 0L,
                    formatId = "best",
                    ext = "mp4",
                    resolution = "",
                    fps = 0,
                    vcodec = "",
                    acodec = "",
                    abr = 0,
                    isLive = false
                )
                
            } catch (e: Exception) {
                Log.e(TAG, "فشل في استخراج معلومات YouTube fallback", e)
                createBasicVideoInfo(url)
            }
        }
    }
    
    /**
     * إنشاء معلومات فيديو أساسية
     */
    private fun createBasicVideoInfo(url: String): VideoInfo {
        return VideoInfo(
            id = url.hashCode().toString(),
            title = "فيديو من ${detectPlatform(url)}",
            description = "",
            duration = 0L,
            uploadDate = "",
            uploader = "",
            uploaderUrl = "",
            viewCount = 0L,
            likeCount = 0L,
            thumbnail = "",
            webpage_url = url,
            originalUrl = url,
            platform = detectPlatform(url),
            fileSize = 0L,
            formatId = "best",
            ext = "mp4",
            resolution = "",
            fps = 0,
            vcodec = "",
            acodec = "",
            abr = 0,
            isLive = false
        )
    }
    
    /**
     * استخراج YouTube video ID من URL
     */
    private fun extractYouTubeVideoId(url: String): String? {
        val patterns = listOf(
            "(?:youtube\\.com/watch\\?v=|youtu\\.be/)([a-zA-Z0-9_-]+)",
            "youtube\\.com/embed/([a-zA-Z0-9_-]+)",
            "youtube\\.com/v/([a-zA-Z0-9_-]+)"
        )
        
        for (pattern in patterns) {
            val regex = Regex(pattern)
            val match = regex.find(url)
            if (match != null) {
                return match.groupValues[1]
            }
        }
        return null
    }
    
    /**
     * تحليل معلومات الفيديو من نتيجة yt-dlp
     */
    private fun parseVideoInfo(jsonOutput: String, originalUrl: String): VideoInfo {
        try {
            val lines = jsonOutput.split("\n").filter { it.trim().startsWith("{") }
            if (lines.isEmpty()) {
                throw RuntimeException("لم يتم العثور على معلومات صحيحة")
            }
            
            val json = JSONObject(lines.first())
            
            return VideoInfo(
                id = json.optString("id", ""),
                title = json.optString("title", "فيديو بدون عنوان"),
                description = json.optString("description", ""),
                duration = json.optLong("duration", 0),
                uploadDate = json.optString("upload_date", ""),
                uploader = json.optString("uploader", ""),
                uploaderUrl = json.optString("uploader_url", ""),
                viewCount = json.optLong("view_count", 0),
                likeCount = json.optLong("like_count", 0),
                thumbnail = json.optString("thumbnail", ""),
                webpage_url = json.optString("webpage_url", originalUrl),
                originalUrl = originalUrl,
                platform = detectPlatform(originalUrl),
                fileSize = json.optLong("filesize", 0),
                formatId = json.optString("format_id", ""),
                ext = json.optString("ext", "mp4"),
                resolution = json.optString("resolution", ""),
                fps = json.optInt("fps", 0),
                vcodec = json.optString("vcodec", ""),
                acodec = json.optString("acodec", ""),
                abr = json.optInt("abr", 0),
                isLive = json.optBoolean("is_live", false)
            )
            
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في تحليل معلومات الفيديو", e)
            throw RuntimeException("فشل في تحليل معلومات الفيديو: ${e.message}")
        }
    }
    
    /**
     * تحليل معلومات قائمة التشغيل
     */
    private fun parsePlaylistInfo(jsonOutput: String): PlaylistInfo {
        try {
            val lines = jsonOutput.split("\n").filter { it.trim().startsWith("{") }
            
            val videos = mutableListOf<VideoInfo>()
            var playlistTitle = "قائمة تشغيل"
            var playlistId = ""
            var uploaderName = ""
            
            lines.forEach { line ->
                try {
                    val json = JSONObject(line)
                    
                    if (json.has("_type") && json.getString("_type") == "playlist") {
                        playlistTitle = json.optString("title", playlistTitle)
                        playlistId = json.optString("id", playlistId)
                        uploaderName = json.optString("uploader", uploaderName)
                    } else {
                        // فيديو في القائمة
                        val videoInfo = parseVideoInfoFromJson(json)
                        videos.add(videoInfo)
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "تخطي سطر غير صحيح في قائمة التشغيل", e)
                }
            }
            
            return PlaylistInfo(
                id = playlistId,
                title = playlistTitle,
                description = "",
                uploaderName = uploaderName,
                videoCount = videos.size,
                videos = videos,
                thumbnail = if (videos.isNotEmpty()) videos[0].thumbnail else ""
            )
            
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في تحليل قائمة التشغيل", e)
            throw RuntimeException("فشل في تحليل قائمة التشغيل: ${e.message}")
        }
    }
    
    /**
     * تحليل التنسيقات من JSON response
     */
    private fun parseFormatsFromJson(jsonOutput: String): List<DownloadFormat> {
        try {
            val formats = mutableListOf<DownloadFormat>()
            
            Log.d(TAG, "بدء تحليل JSON للتنسيقات")
            
            // تنظيف النص وتقسيمه إلى أسطر
            val cleanedOutput = jsonOutput.trim()
            val lines = cleanedOutput.split("\n")
            
            Log.d(TAG, "عدد الأسطر في النتيجة: ${lines.size}")
            
            // البحث عن أسطر JSON صحيحة
            val jsonLines = lines.filter { line ->
                val trimmed = line.trim()
                trimmed.startsWith("{") && trimmed.endsWith("}")
            }
            
            Log.d(TAG, "عدد أسطر JSON الصحيحة: ${jsonLines.size}")
            
            if (jsonLines.isEmpty()) {
                // محاولة تحليل النص كاملاً كـ JSON واحد
                if (cleanedOutput.startsWith("{") && cleanedOutput.endsWith("}")) {
                    Log.d(TAG, "محاولة تحليل النص كاملاً كـ JSON واحد")
                    try {
                        val json = JSONObject(cleanedOutput)
                        val extractedFormats = extractFormatsFromSingleJson(json)
                        formats.addAll(extractedFormats)
                        Log.d(TAG, "تم استخراج ${extractedFormats.size} تنسيق من JSON واحد")
                    } catch (e: Exception) {
                        Log.e(TAG, "فشل في تحليل JSON الكامل", e)
                    }
                }
            } else {
                // تحليل كل سطر JSON منفصل
                jsonLines.forEach { line ->
                    try {
                        val json = JSONObject(line.trim())
                        val extractedFormats = extractFormatsFromSingleJson(json)
                        formats.addAll(extractedFormats)
                        Log.d(TAG, "تم استخراج ${extractedFormats.size} تنسيق من سطر JSON")
                    } catch (e: Exception) {
                        Log.w(TAG, "تخطي سطر JSON غير صحيح: ${line.take(100)}...", e)
                    }
                }
            }
            
            // إزالة التنسيقات المكررة
            val uniqueFormats = formats.distinctBy { "${it.formatId}_${it.quality}_${it.extension}" }
            
            Log.d(TAG, "إجمالي التنسيقات المستخرجة: ${formats.size}")
            Log.d(TAG, "التنسيقات الفريدة: ${uniqueFormats.size}")
            
            return uniqueFormats
            
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في تحليل التنسيقات من JSON", e)
            return emptyList()
        }
    }
    
    /**
     * استخراج التنسيقات من JSON object واحد
     */
    private fun extractFormatsFromSingleJson(json: JSONObject): List<DownloadFormat> {
        val formats = mutableListOf<DownloadFormat>()
        
        try {
            // التحقق من وجود قائمة formats في JSON
            if (json.has("formats")) {
                val formatsArray = json.getJSONArray("formats")
                Log.d(TAG, "وُجدت قائمة formats مع ${formatsArray.length()} عنصر")
                
                for (i in 0 until formatsArray.length()) {
                    try {
                        val formatJson = formatsArray.getJSONObject(i)
                        val format = parseFormatFromJson(formatJson)
                        if (format != null) {
                            formats.add(format)
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "خطأ في تحليل تنسيق رقم $i", e)
                    }
                }
            } else {
                // إذا لم تكن هناك قائمة formats، فربما هذا format واحد
                if (json.has("format_id") || json.has("ext")) {
                    Log.d(TAG, "محاولة تحليل JSON كتنسيق واحد")
                    val format = parseFormatFromJson(json)
                    if (format != null) {
                        formats.add(format)
                    }
                } else {
                    Log.d(TAG, "لم يتم العثور على formats أو format_id في JSON")
                }
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في استخراج formats من JSON", e)
        }
        
        return formats
    }
    
    /**
     * تحليل تنسيق واحد من JSON
     */
    private fun parseFormatFromJson(json: JSONObject): DownloadFormat? {
        try {
            val formatId = json.optString("format_id", "").takeIf { it.isNotEmpty() } 
                ?: json.optString("id", "")
                ?: "unknown_${System.currentTimeMillis()}"
            
            val ext = json.optString("ext", "mp4")
            
            // استخراج معلومات الفيديو
            val height = json.optInt("height", 0).takeIf { it > 0 }
            val width = json.optInt("width", 0).takeIf { it > 0 }
            val fps = json.optInt("fps", 0).takeIf { it > 0 }
            
            // استخراج معلومات الملف
            val fileSize = json.optLong("filesize", 0).takeIf { it > 0 }
                ?: json.optLong("filesize_approx", 0).takeIf { it > 0 }
            
            // استخراج معلومات الكودك
            val vcodec = json.optString("vcodec", "").takeIf { 
                it.isNotEmpty() && it != "none" && it != "null" 
            }
            val acodec = json.optString("acodec", "").takeIf { 
                it.isNotEmpty() && it != "none" && it != "null" 
            }
            
            // استخراج معلومات الصوت
            val audioBitrate = json.optInt("abr", 0).takeIf { it > 0 }
                ?: json.optInt("tbr", 0).takeIf { it > 0 }
            
            // تحديد نوع المحتوى
            val hasVideo = vcodec != null && height != null && height > 0
            val hasAudio = acodec != null || audioBitrate != null || ext in listOf("mp3", "m4a", "webm", "ogg")
            
            // إنشاء نص الجودة
            val quality = when {
                height != null -> "${height}p"
                audioBitrate != null -> "${audioBitrate}kbps"
                hasAudio && !hasVideo -> "صوت"
                hasVideo && !hasAudio -> "فيديو"
                else -> "غير محدد"
            }
            
            Log.d(TAG, "تحليل تنسيق: $formatId - $quality - $ext")
            Log.d(TAG, "فيديو: $hasVideo (${height}p), صوت: $hasAudio (${audioBitrate}kbps)")
            
            return DownloadFormat(
                formatId = formatId,
                extension = ext,
                quality = quality,
                height = height,
                width = width,
                fps = fps,
                vcodec = vcodec,
                acodec = acodec,
                audioBitrate = audioBitrate,
                fileSize = fileSize,
                hasVideo = hasVideo,
                hasAudio = hasAudio
            )
            
        } catch (e: Exception) {
            Log.w(TAG, "خطأ في تحليل format JSON", e)
            return null
        }
    }
    
    /**
     * تحليل التنسيقات المتاحة
     */
    private fun parseFormats(output: String): List<DownloadFormat> {
        try {
            val formats = mutableListOf<DownloadFormat>()
            val lines = output.split("\n")
            
            // البحث عن خطوط التنسيقات
            var inFormatsSection = false
            
            lines.forEach { line ->
                if (line.contains("Available formats") || line.contains("format code")) {
                    inFormatsSection = true
                    return@forEach
                }
                
                if (!inFormatsSection || line.trim().isEmpty()) {
                    return@forEach
                }
                
                try {
                    val format = parseFormatLine(line)
                    if (format != null) {
                        formats.add(format)
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "تخطي تنسيق غير صحيح: $line", e)
                }
            }
            
            return formats.distinctBy { "${it.formatId}_${it.quality}" }
            
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في تحليل التنسيقات", e)
            return emptyList()
        }
    }
    
    /**
     * تحليل سطر تنسيق واحد
     */
    private fun parseFormatLine(line: String): DownloadFormat? {
        try {
            val parts = line.trim().split("\\s+".toRegex())
            if (parts.size < 3) return null
            
            val formatId = parts[0]
            val extension = parts[1]
            val resolution = if (parts.size > 2) parts[2] else ""
            
            // استخراج معلومات الجودة
            val height = extractHeight(resolution)
            // استخدام القيمة الافتراضية 0 عند null
            val nonNullHeight = height ?: 0
            val hasVideo = resolution.contains("x") || nonNullHeight > 0
            val hasAudio = line.contains("audio") || extension in listOf("mp3", "m4a", "webm")
            
            return DownloadFormat(
                formatId = formatId,
                extension = extension,
                quality = resolution,
                height = height,
                width = extractWidth(resolution),
                fps = extractFps(line),
                vcodec = extractVideoCodec(line),
                acodec = extractAudioCodec(line),
                audioBitrate = extractAudioBitrate(line),
                fileSize = extractFileSize(line),
                hasVideo = hasVideo,
                hasAudio = hasAudio
            )
            
        } catch (e: Exception) {
            Log.w(TAG, "خطأ في تحليل سطر التنسيق: $line", e)
            return null
        }
    }
    
    /**
     * استخراج ارتفاع الفيديو من النص
     */
    private fun extractHeight(resolution: String): Int? {
        val pattern = Pattern.compile("(\\d+)x(\\d+)|\\b(\\d+)p\\b")
        val matcher = pattern.matcher(resolution)
        if (!matcher.find()) return null
        matcher.group(2)?.let { return it.toInt() }
        matcher.group(3)?.let { return it.toInt() }
        return null
    }
    
    /**
     * استخراج عرض الفيديو من النص
     */
    private fun extractWidth(resolution: String): Int? {
        val pattern = Pattern.compile("(\\d+)x(\\d+)")
        val matcher = pattern.matcher(resolution)
        if (!matcher.find()) return null
        return matcher.group(1)?.toInt()
    }
    
    /**
     * استخراج معدل الإطارات
     */
    private fun extractFps(line: String): Int? {
        val pattern = Pattern.compile("(\\d+)fps")
        val matcher = pattern.matcher(line)
        if (!matcher.find()) return null
        return matcher.group(1)?.toInt()
    }
    
    /**
     * استخراج كودك الفيديو
     */
    private fun extractVideoCodec(line: String): String? {
        val codecs = listOf("h264", "h265", "vp9", "vp8", "av01")
        return codecs.find { line.contains(it, ignoreCase = true) }
    }
    
    /**
     * استخراج كودك الصوت
     */
    private fun extractAudioCodec(line: String): String? {
        val codecs = listOf("aac", "mp3", "opus", "vorbis", "m4a")
        return codecs.find { line.contains(it, ignoreCase = true) }
    }
    
    /**
     * استخراج معدل بت الصوت
     */
    private fun extractAudioBitrate(line: String): Int? {
        val pattern = Pattern.compile("(\\d+)k")
        val matcher = pattern.matcher(line)
        if (!matcher.find()) return null
        return matcher.group(1)?.toInt()
    }
    
    /**
     * استخراج حجم الملف
     */
    private fun extractFileSize(line: String): Long? {
        val pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)(MiB|GiB|KiB|MB|GB|KB)")
        val matcher = pattern.matcher(line)
        if (!matcher.find()) return null
        val sizeStr = matcher.group(1) ?: return null
        val unit = matcher.group(2) ?: return null
        val sizeValue = sizeStr.toDoubleOrNull() ?: return null
        return when (unit.uppercase()) {
            "KIB", "KB" -> (sizeValue * 1024).toLong()
            "MIB", "MB" -> (sizeValue * 1024 * 1024).toLong()
            "GIB", "GB" -> (sizeValue * 1024 * 1024 * 1024).toLong()
            else -> sizeValue.toLong()
        }
    }
    
    /**
     * تحليل معلومات الفيديو من JSON
     */
    private fun parseVideoInfoFromJson(json: JSONObject): VideoInfo {
        return VideoInfo(
            id = json.optString("id", ""),
            title = json.optString("title", "فيديو بدون عنوان"),
            description = json.optString("description", ""),
            duration = json.optLong("duration", 0),
            uploadDate = json.optString("upload_date", ""),
            uploader = json.optString("uploader", ""),
            uploaderUrl = json.optString("uploader_url", ""),
            viewCount = json.optLong("view_count", 0),
            likeCount = json.optLong("like_count", 0),
            thumbnail = json.optString("thumbnail", ""),
            webpage_url = json.optString("webpage_url", ""),
            originalUrl = json.optString("webpage_url", ""),
            platform = json.optString("extractor", "unknown"),
            fileSize = json.optLong("filesize", 0),
            formatId = json.optString("format_id", ""),
            ext = json.optString("ext", "mp4"),
            resolution = json.optString("resolution", ""),
            fps = json.optInt("fps", 0),
            vcodec = json.optString("vcodec", ""),
            acodec = json.optString("acodec", ""),
            abr = json.optInt("abr", 0),
            isLive = json.optBoolean("is_live", false)
        )
    }
}
