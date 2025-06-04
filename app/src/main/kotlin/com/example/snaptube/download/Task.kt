package com.example.snaptube.download

import com.example.snaptube.models.DownloadFormat
import com.example.snaptube.models.VideoInfo
import kotlinx.coroutines.Job
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.math.roundToInt
import kotlinx.serialization.Contextual

@Serializable
enum class TypeInfo {
    URL;
    
    val id: String get() = name
}

private fun makeId(url: String, type: TypeInfo, preferences: DownloadPreferences): String =
    "${url}_${type.id}_${preferences.hashCode()}"

@Serializable
data class Task(
    val url: String,
    val type: TypeInfo = TypeInfo.URL,
    val preferences: DownloadPreferences,
    val id: String = makeId(url, type, preferences),
) : Comparable<Task> {

    val timeCreated: Long = System.currentTimeMillis()
    
    // Current state of the task
    @Transient
    var downloadState: DownloadState = DownloadState.Idle
    
    // Current view state with video information
    @Transient 
    var viewState: ViewState = ViewState()
    
    // Output file path when download is completed
    @Transient
    var outputPath: String? = null
    
    // Current progress (0.0 to 1.0)
    @Transient
    var progress: Float = 0f
    
    // Progress text for display
    @Transient
    var progressText: String = ""
    
    // Error information if download failed
    @Transient
    var error: Throwable? = null

    override fun compareTo(other: Task): Int {
        return timeCreated.compareTo(other.timeCreated)
    }
    @Serializable
    sealed interface DownloadState : Comparable<DownloadState> {

        interface Cancelable {
            @Transient
            val job: Job
            val taskId: String
            val action: RestartableAction
        }

        interface Restartable {
            val action: RestartableAction
        }

        @Serializable
        data object Idle : DownloadState

        @Serializable
        data class FetchingInfo(
            @Transient override val job: Job = Job(),
            override val taskId: String,
        ) : DownloadState, Cancelable {
            override val action: RestartableAction = RestartableAction.FetchInfo
        }

        @Serializable
        data object ReadyWithInfo : DownloadState

        @Serializable
        data class Running(
            @Transient override val job: Job = Job(),
            override val taskId: String,
            val progress: Float = PROGRESS_INDETERMINATE,
            val progressText: String = "",
        ) : DownloadState, Cancelable {
            override val action: RestartableAction = RestartableAction.Download
        }

        @Serializable
        data class Canceled(
            override val action: RestartableAction,
            val progress: Float? = null
        ) : DownloadState, Restartable

        @Serializable
        data class Error(
            @Transient val throwable: Throwable = Throwable(),
            override val action: RestartableAction,
        ) : DownloadState, Restartable

        @Serializable
        data class Completed(val filePath: String?) : DownloadState

        override fun compareTo(other: DownloadState): Int {
            return ordinal - other.ordinal
        }

        private val ordinal: Int
            get() = when (this) {
                is Canceled -> 4
                is Error -> 5
                is Completed -> 6
                Idle -> 3
                is FetchingInfo -> 2
                ReadyWithInfo -> 1
                is Running -> 0
            }

        companion object {
            const val PROGRESS_INDETERMINATE = -1f
        }
    }

    @Serializable
    sealed interface RestartableAction {
        @Serializable
        data object FetchInfo : RestartableAction

        @Serializable
        data object Download : RestartableAction
    }

    @Serializable
    data class ViewState(
        val url: String = "https://www.example.com",
        val title: String = "",
        val uploader: String = "",
        val extractorKey: String = "",
        val duration: Int = 0,
        val fileSizeApprox: Double = .0,
        val thumbnailUrl: String? = null,
        @Contextual
        val videoFormats: List<DownloadFormat>? = null,
        @Contextual
        val audioOnlyFormats: List<DownloadFormat>? = null,
    ) {
        companion object {
            fun fromVideoInfo(info: VideoInfo): ViewState = ViewState(
                url = info.originalUrl,
                title = info.title,
                uploader = info.uploader ?: "",
                extractorKey = info.platform,
                duration = info.duration?.let { it.toInt() } ?: 0,
                fileSizeApprox = info.fileSize?.toDouble() ?: 0.0,
                thumbnailUrl = info.thumbnail,
            )
        }
    }
}

@Serializable
data class DownloadPreferences(
    val formatId: String = "",
    val audioOnly: Boolean = false,
    val extractAudio: Boolean = false,
    val audioFormat: String = "mp3",
    val videoFormat: String = "mp4",
    val downloadLocation: String = "",
    val filename: String = "",
    val overwriteFiles: Boolean = false,
    val restrictFilenames: Boolean = true,
    val mergeOutputFormat: String = "",
    val embedSubs: Boolean = false,
    val embedThumbnail: Boolean = false,
    val embedMetadata: Boolean = true,
    val cropThumbnail: Boolean = false,
    val sponsorBlock: Boolean = false,
    val aria2c: Boolean = false,
    val concurrentConnections: Int = 1,
    val limitRate: String = "",
    val privateMode: Boolean = false,
    val useDownloadArchive: Boolean = false,
    val splitByChapter: Boolean = false,
    val downloadPlaylist: Boolean = false,
    val playlistItems: String = "",
    val playlistStart: Int = 1,
    val playlistEnd: Int = 0,
    val playlistReverse: Boolean = false,
    val playlistRandom: Boolean = false,
    val maxDownloads: Int = 0,
    val subtitleLanguage: String = "",
    val liveFromStart: Boolean = false,
    val cookies: String = "",
    val newTitle: String = "",
    val commandDirectory: String = "",
    val proxyUrl: String = "",
    val userAgent: String = "",
    val referer: String = "",
    val retries: Int = 10,
    val fragmentRetries: Int = 10,
    val httpChunkSize: Long = 0,
    val rateLimit: Double = 0.0,
    val bufferSize: String = "",
    val ignoreErrors: Boolean = true,
    val noCheckCertificate: Boolean = false,
    val preferFreeFormats: Boolean = false,
    val forceJson: Boolean = false,
    val addHeader: List<String> = emptyList(),
    val geoBypass: Boolean = false,
    val geoBypassCountry: String = "",
    val geoBypassIpBlock: String = "",
) {
    companion object {
        fun createFromPreferences(): DownloadPreferences {
            return DownloadPreferences()
        }
    }
}

@Serializable
data class CommandTemplate(
    val id: Long = 0,
    val name: String = "",
    val template: String = "",
    val description: String = "",
    val isCustom: Boolean = true
)

// Extension functions for DownloadState
fun Task.DownloadState.isActive(): Boolean = when (this) {
    is Task.DownloadState.FetchingInfo, is Task.DownloadState.Running -> true
    else -> false
}

fun Task.DownloadState.isCompleted(): Boolean = when (this) {
    is Task.DownloadState.Completed -> true
    else -> false
}

fun Task.DownloadState.isError(): Boolean = when (this) {
    is Task.DownloadState.Error -> true
    else -> false
}

fun Task.DownloadState.isCanceled(): Boolean = when (this) {
    is Task.DownloadState.Canceled -> true
    else -> false
}

fun Task.DownloadState.isPending(): Boolean = when (this) {
    is Task.DownloadState.Idle, is Task.DownloadState.ReadyWithInfo -> true
    else -> false
}

fun Task.DownloadState.canBeCanceled(): Boolean = this is Task.DownloadState.Cancelable

fun Task.DownloadState.canBeRestarted(): Boolean = this is Task.DownloadState.Restartable

fun Task.DownloadState.getProgress(): Float? = when (this) {
    is Task.DownloadState.Running -> progress
    is Task.DownloadState.Canceled -> progress
    else -> null
}

fun Task.DownloadState.getProgressText(): String = when (this) {
    is Task.DownloadState.Running -> progressText
    else -> ""
}
