package com.example.simplenotesapp.repository

import com.example.simplenotesapp.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(noteId: String): Note?
    suspend fun togglePin(noteId: String)
    suspend fun toggleChecklistItem(noteId: String, itemId: String)
    suspend fun duplicateNote(noteId: String)
    suspend fun getNoteById(id: String): Note?
}