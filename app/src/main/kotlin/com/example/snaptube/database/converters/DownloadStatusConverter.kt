package com.example.snaptube.database.converters

import androidx.room.TypeConverter
import com.example.snaptube.download.DownloadStatus

class DownloadStatusConverter {
    
    @TypeConverter
    fun fromDownloadStatus(status: DownloadStatus): String {
        return status.name
    }
    
    @TypeConverter
    fun toDownloadStatus(statusName: String): DownloadStatus {
        return try {
            DownloadStatus.valueOf(statusName)
        } catch (e: IllegalArgumentException) {
            DownloadStatus.PENDING // Default fallback
        }
    }
}
