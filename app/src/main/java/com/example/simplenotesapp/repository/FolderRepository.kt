package com.example.simplenotesapp.repository

import com.example.simplenotesapp.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    fun addFolder(folder: Folder)
    suspend fun updateFolder(folder: Folder)
    suspend fun deleteFolder(folderId: String): Folder?
    suspend fun togglePin(folderId: String)
    suspend fun getFolderById(id: String): Folder?
    suspend fun getAllFolders() : Flow<List<Folder>>

}