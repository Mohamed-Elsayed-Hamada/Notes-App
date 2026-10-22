package com.example.simplefoldersapp.view.uimodel

import com.example.simplenotesapp.model.Folder

sealed interface FoldersScreenIntent {
    data class SearchFolder(val query: String) : FoldersScreenIntent
    data class TogglePin(val folderId: String) : FoldersScreenIntent
    data class Deletefolder(val folderId: String) : FoldersScreenIntent
    data class SetActiveMenufolder(val folderId: String?) : FoldersScreenIntent
    data class SetSort(val sort: String) : FoldersScreenIntent    // ----- ask about it
    data class SetActiveTab(val tab: String) : FoldersScreenIntent
    data object UndoDelete : FoldersScreenIntent
    data class CreateQuickFolder(val folder: Folder) : FoldersScreenIntent
    data class NavigateToFolderDetails(val id: String) : FoldersScreenIntent


}