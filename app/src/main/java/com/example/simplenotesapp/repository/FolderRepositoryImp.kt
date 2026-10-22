package com.example.simplenotesapp.repository

import com.example.simplenotesapp.model.Folder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FolderRepositoryImp : FolderRepository {
    private val _folders = MutableStateFlow<List<Folder>>(emptyList())

    override fun addFolder(folder: Folder) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFolder(folder: Folder) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFolder(folderId: String): Folder? {
        TODO("Not yet implemented")
    }



    override suspend fun togglePin(folderId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getFolderById(id: String): Folder? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFolders(): Flow<List<Folder>> {
        TODO("Not yet implemented")
    }
}