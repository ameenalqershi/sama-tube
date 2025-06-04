package com.example.snaptube.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snaptube.models.VideoInfo
import com.example.snaptube.repository.DownloadRepository
import com.example.snaptube.repository.VideoInfoRepository
import com.example.snaptube.database.entities.DownloadEntity
import com.example.snaptube.utils.FileUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.io.File

class LibraryViewModel : ViewModel(), KoinComponent {
    
    // الحصول على التبعيات من Koin
    private val downloadRepository: DownloadRepository = get()
    private val videoInfoRepository: VideoInfoRepository = get()
    private val fileUtils: FileUtils = get()
    
    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    init {
        loadCompletedDownloads()
        observeSearchQuery()
    }
    
    private fun loadCompletedDownloads() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                downloadRepository.getCompletedDownloads().collect { downloads ->
                    val libraryItems = downloads.mapNotNull { entity ->
                        // التحقق من وجود الملف فعلياً
                        val file = File(entity.downloadPath)
                        if (file.exists()) {
                            LibraryItem(
                                id = entity.id,
                                title = entity.title,
                                author = entity.author ?: "غير معروف",
                                filePath = entity.downloadPath,
                                thumbnailUrl = entity.thumbnail,
                                duration = entity.duration.toString(),
                                fileSize = file.length(),
                                downloadDate = (entity.completedAt ?: entity.createdAt).time,
                                format = entity.fileExtension,
                                isAudioOnly = entity.audioOnly
                            )
                        } else {
                            // حذف السجل إذا لم يعد الملف موجوداً
                            viewModelScope.launch {
                                downloadRepository.deleteDownload(entity.id)
                            }
                            null
                        }
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        allItems = libraryItems,
                        filteredItems = filterItems(libraryItems, _searchQuery.value),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "خطأ في تحميل المكتبة"
                )
                Timber.e(e, "خطأ في تحميل ملفات المكتبة")
            }
        }
    }
    
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery.debounce(300).collect { query ->
                val filtered = filterItems(_uiState.value.allItems, query)
                _uiState.value = _uiState.value.copy(filteredItems = filtered)
            }
        }
    }
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun clearSearch() {
        _searchQuery.value = ""
    }
    
    fun deleteFile(itemId: String) {
        viewModelScope.launch {
            try {
                val item = _uiState.value.allItems.find { it.id == itemId }
                if (item != null) {
                    // حذف الملف من النظام
                    val file = File(item.filePath)
                    if (file.exists()) {
                        file.delete()
                    }
                    
                    // حذف السجل من قاعدة البيانات
                    downloadRepository.deleteDownload(itemId)
                    Timber.d("تم حذف الملف: ${item.title}")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "خطأ في حذف الملف"
                )
                Timber.e(e, "خطأ في حذف الملف")
            }
        }
    }
    
    fun shareFile(itemId: String) {
        viewModelScope.launch {
            try {
                val item = _uiState.value.allItems.find { it.id == itemId }
                if (item != null) {
                    val file = File(item.filePath)
                    if (file.exists()) {
                        fileUtils.shareFile(file)
                        Timber.d("مشاركة الملف: ${item.title}")
                    } else {
                        Timber.w("الملف غير موجود: ${item.filePath}")
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "خطأ في مشاركة الملف")
            }
        }
    }
    
    fun playFile(itemId: String) {
        viewModelScope.launch {
            try {
                val item = _uiState.value.allItems.find { it.id == itemId }
                if (item != null) {
                    val file = File(item.filePath)
                    if (file.exists()) {
                        fileUtils.openFile(file)
                        Timber.d("فتح الملف: ${item.title}")
                    } else {
                        _uiState.value = _uiState.value.copy(
                            error = "الملف غير موجود: ${item.title}"
                        )
                        Timber.w("الملف غير موجود: ${item.filePath}")
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "لم يتم العثور على الملف"
                    )
                    Timber.w("عنصر غير موجود في المكتبة: $itemId")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "خطأ في فتح الملف: ${e.message}"
                )
                Timber.e(e, "خطأ في فتح الملف")
            }
        }
    }
    
    fun sortBy(sortType: LibrarySortType) {
        val sortedItems = when (sortType) {
            LibrarySortType.DATE_DESC -> _uiState.value.allItems.sortedByDescending { it.downloadDate }
            LibrarySortType.DATE_ASC -> _uiState.value.allItems.sortedBy { it.downloadDate }
            LibrarySortType.NAME_ASC -> _uiState.value.allItems.sortedBy { it.title }
            LibrarySortType.NAME_DESC -> _uiState.value.allItems.sortedByDescending { it.title }
            LibrarySortType.SIZE_DESC -> _uiState.value.allItems.sortedByDescending { it.fileSize }
            LibrarySortType.SIZE_ASC -> _uiState.value.allItems.sortedBy { it.fileSize }
        }
        
        _uiState.value = _uiState.value.copy(
            allItems = sortedItems,
            filteredItems = filterItems(sortedItems, _searchQuery.value),
            currentSortType = sortType
        )
    }
    
    fun filterByType(filterType: LibraryFilterType) {
        val filteredByType = when (filterType) {
            LibraryFilterType.ALL -> _uiState.value.allItems
            LibraryFilterType.VIDEO -> _uiState.value.allItems.filter { !it.isAudioOnly }
            LibraryFilterType.AUDIO -> _uiState.value.allItems.filter { it.isAudioOnly }
        }
        
        _uiState.value = _uiState.value.copy(
            filteredItems = filterItems(filteredByType, _searchQuery.value),
            currentFilterType = filterType
        )
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    private fun filterItems(items: List<LibraryItem>, query: String): List<LibraryItem> {
        if (query.isBlank()) return items
        
        return items.filter { item ->
            item.title.contains(query, ignoreCase = true) ||
            item.author.contains(query, ignoreCase = true)
        }
    }
    
    fun getStorageInfo(): StorageInfo {
        val libraryItems = _uiState.value.allItems
        val totalSize = libraryItems.sumOf { it.fileSize }
        val videoCount = libraryItems.count { !it.isAudioOnly }
        val audioCount = libraryItems.count { it.isAudioOnly }
        
        return StorageInfo(
            totalFiles = libraryItems.size,
            totalSize = totalSize,
            videoCount = videoCount,
            audioCount = audioCount
        )
    }
}

data class LibraryUiState(
    val allItems: List<LibraryItem> = emptyList(),
    val filteredItems: List<LibraryItem> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val currentSortType: LibrarySortType = LibrarySortType.DATE_DESC,
    val currentFilterType: LibraryFilterType = LibraryFilterType.ALL
)

data class LibraryItem(
    val id: String,
    val title: String,
    val author: String,
    val filePath: String,
    val thumbnailUrl: String?,
    val duration: String,
    val fileSize: Long,
    val downloadDate: Long,
    val format: String,
    val isAudioOnly: Boolean
) {
    fun getFormattedFileSize(): String {
        val kb = fileSize / 1024
        val mb = kb / 1024
        val gb = mb / 1024
        
        return when {
            gb > 0 -> "${gb}.${(mb % 1024) / 100} GB"
            mb > 0 -> "${mb}.${(kb % 1024) / 100} MB"
            else -> "$kb KB"
        }
    }
    
    fun getFormattedDate(): String {
        val date = java.util.Date(downloadDate)
        val formatter = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
        return formatter.format(date)
    }
}

enum class LibrarySortType {
    DATE_DESC,
    DATE_ASC,
    NAME_ASC,
    NAME_DESC,
    SIZE_DESC,
    SIZE_ASC
}

enum class LibraryFilterType {
    ALL,
    VIDEO,
    AUDIO
}
