package com.example.snaptube.ui.components

/**
 * Sealed interface representing all possible UI actions for download tasks
 */
sealed interface UiAction {
    // Basic download control actions
    data object Cancel : UiAction
    data object Delete : UiAction
    data object Resume : UiAction
    data object Restart : UiAction
    data object Pause : UiAction
    data object RetryFailed : UiAction
    data object DownloadAgain : UiAction
    
    // File operations
    data class OpenFile(val filePath: String) : UiAction
    data class ShareFile(val filePath: String) : UiAction
    data object ShowInFolder : UiAction
    
    // Clipboard operations
    data object CopyLink : UiAction
    data object CopyVideoURL : UiAction
    data class CopyErrorReport(val throwable: Throwable) : UiAction
}
