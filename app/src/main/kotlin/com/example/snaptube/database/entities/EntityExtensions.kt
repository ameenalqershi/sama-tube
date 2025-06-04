package com.example.snaptube.database.entities

import com.example.snaptube.models.VideoInfo
import com.example.snaptube.models.PlaylistInfo
import com.example.snaptube.models.DownloadFormat
import com.example.snaptube.download.DownloadTask
import com.example.snaptube.download.DownloadStatus
import java.util.Date

/**
 * Extension functions للتحويل بين الكيانات والنماذج
 */

// تحويل VideoInfoEntity إلى VideoInfo
fun VideoInfoEntity.toVideoInfo(): VideoInfo {
    return VideoInfo(
        id = this.videoId,
        title = this.title,
        description = this.description ?: "",
        duration = this.duration,
        uploadDate = this.uploadDate ?: "",
        uploader = this.author ?: "",
        uploaderUrl = "",
        viewCount = this.viewCount,
        likeCount = 0,
        thumbnail = this.thumbnail ?: "",
        webpage_url = this.url,
        originalUrl = this.url,
        platform = this.platform,
        fileSize = this.fileSize,
        formatId = "",
        ext = "mp4",
        resolution = "",
        fps = 0,
        vcodec = "",
        acodec = "",
        abr = 0,
        isLive = false,
        categories = emptyList(),
        tags = emptyList(),
        language = this.language ?: "",
        ageLimit = 0,
        subtitles = emptyList(),
        formats = emptyList()
    )
}

// تحويل VideoInfo إلى VideoInfoEntity
fun VideoInfo.toVideoInfoEntity(): VideoInfoEntity {
    return VideoInfoEntity(
        videoId = this.id,
        url = this.webpage_url,
        title = this.title,
        description = this.description,
        duration = this.duration,
        uploadDate = this.uploadDate,
        author = this.uploader,
        thumbnail = this.thumbnail,
        platform = this.platform,
        viewCount = this.viewCount,
        fileSize = this.fileSize,
        language = this.language,
        createdAt = Date(),
        lastAccessed = Date(),
        cacheExpiryDate = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000), // 24 hours
        accessCount = 1,
        isValid = true
    )
}

// تحويل PlaylistEntity إلى PlaylistInfo
fun PlaylistEntity.toPlaylistInfo(): PlaylistInfo {
    return PlaylistInfo(
        id = this.playlistId,
        title = this.title,
        description = this.description ?: "",
        uploaderName = this.author ?: "",
        uploaderUrl = "",
        videoCount = this.videoCount,
        videos = emptyList(), // سيتم تحميلها بشكل منفصل
        thumbnail = this.thumbnail ?: "",
        createdDate = "",
        updatedDate = "",
        platform = this.platform,
        originalUrl = this.url,
        isPublic = true,
        totalDuration = this.totalDuration,
        viewCount = 0,
        likeCount = 0,
        tags = emptyList(),
        language = "",
        category = ""
    )
}

// تحويل PlaylistInfo إلى PlaylistEntity
fun PlaylistInfo.toPlaylistEntity(): PlaylistEntity {
    return PlaylistEntity(
        playlistId = this.id,
        url = this.originalUrl,
        title = this.title,
        description = this.description,
        author = this.uploaderName,
        thumbnail = this.thumbnail,
        platform = this.platform,
        videoCount = this.videoCount,
        totalDuration = this.totalDuration,
        createdAt = Date(),
        lastAccessed = Date(),
        accessCount = 1,
        isBookmarked = false,
        downloadedCount = 0,
        failedCount = 0,
        downloadProgress = 0f,
        totalSize = 0,
        downloadStartedAt = null,
        downloadCompletedAt = null
    )
}

// تحويل DownloadEntity إلى DownloadTask باستخدام الدالة المدمجة
fun DownloadEntity.toDownloadTask(): DownloadTask = DownloadTask.fromEntity(this)

// تحويل DownloadTask إلى DownloadEntity باستخدام الدالة المدمجة
fun DownloadTask.toDownloadEntity(): DownloadEntity = this.toEntity()
