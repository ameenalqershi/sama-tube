package com.example.snaptube.download

import com.example.snaptube.models.VideoInfo
import com.example.snaptube.models.DownloadFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.roundToInt

object TaskFactory {
    
    suspend fun createWithVideoResult(
        info: VideoInfo,
        preferences: DownloadPreferences
    ): TaskWithState {
        
        val task = Task(url = info.originalUrl, preferences = preferences)
        task.downloadState = Task.DownloadState.ReadyWithInfo
        task.viewState = Task.ViewState.fromVideoInfo(info = info)
        
        return TaskWithState(task)
    }
    
    suspend fun createWithPlaylistResult(
        playlistUrl: String,
        indexList: List<Int>,
        playlistResult: PlaylistResult,
        preferences: DownloadPreferences,
    ): List<TaskWithState> {
        checkNotNull(playlistResult.entries)
        val indexEntryMap = indexList.associateWith { index -> playlistResult.entries[index - 1] }

        val taskList = indexEntryMap.map { (index, entry) ->
            val viewState = Task.ViewState(
                url = entry.url ?: "",
                title = entry.title ?: "${playlistResult.title} - $index",
                duration = entry.duration?.roundToInt() ?: 0,
                uploader = entry.uploader ?: entry.channel ?: playlistResult.channel ?: "",
                thumbnailUrl = (entry.thumbnails?.lastOrNull()?.url) ?: "",
            )
            val task = Task(
                url = playlistUrl,
                preferences = preferences,
                type = TypeInfo.URL
            )
            task.viewState = viewState
            TaskWithState(task)
        }

        return taskList
    }
    
    data class TaskWithState(val task: Task)
}

data class PlaylistResult(
    val title: String = "",
    val uploader: String? = null,
    val channel: String? = null,
    val entries: List<PlaylistEntry>? = null
)

data class PlaylistEntry(
    val url: String? = null,
    val title: String? = null,
    val duration: Double? = null,
    val uploader: String? = null,
    val channel: String? = null,
    val thumbnails: List<Thumbnail>? = null
)

data class Thumbnail(
    val url: String,
    val width: Int? = null,
    val height: Int? = null
)
