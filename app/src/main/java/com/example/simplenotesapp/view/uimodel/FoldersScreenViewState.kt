package com.example.simplenotesapp.view.uimodel

import com.example.simplenotesapp.model.Folder

data class FoldersScreenViewState(
    val isLoading: Boolean = false,
    val folders: List<Folder> = emptyList(),
    val searchQuery: String = "",
    val activeMenuFolderId: String? = null,
    val selectedSort: String = "Recent",
    val userMessage: String? = null
) {
    val displayFolders: List<Folder>
        get() = if (searchQuery.isBlank()) {
            folders
        } else {
            folders.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.categoryList.any { category ->
                            category.contains(searchQuery, ignoreCase = true)
                        }
            }
        }
    val pinnedFolders: List<Folder>
        get() = displayFolders.filter { it.isPinned }

    val allNotesSortedByPinned: List<Folder>
        get() = displayFolders.sortedBy { it.isPinned }

    val totalFoldersCount: Int = folders.size
}