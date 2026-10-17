package com.example.simplenotesapp.repository

import com.example.simplenotesapp.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImp @Inject constructor() : NoteRepository {

    private val _notesFlow = MutableStateFlow(createInitialNotes())

    override fun getNotes(): Flow<List<Note>> = _notesFlow.asStateFlow()

    override suspend fun addNote(note: Note) {
        _notesFlow.update { currentList ->
            listOf(note) + currentList
        }
    }

    override suspend fun updateNote(note: Note) {
        _notesFlow.update { currentList ->
            currentList.map { if (it.id == note.id) note else it }
        }
    }

    override suspend fun deleteNote(noteId: String): Note? {
        var removedNote: Note? = null
        _notesFlow.update { currentList ->
            removedNote = currentList.find { it.id == noteId }
            currentList.filterNot { it.id == noteId }
        }
        return removedNote
    }

    override suspend fun togglePin(noteId: String) {
        _notesFlow.update { currentList ->
            currentList.map { note ->
                if (note.id == noteId) {
                    note.copy(isPinned = !note.isPinned)
                } else {
                    note
                }
            }
        }
    }

    override suspend fun toggleChecklistItem(noteId: String, itemId: String) {
        _notesFlow.update { currentList ->
            currentList.map { note ->
                if (note.id == noteId) {
                    val updatedChecklist = note.checklist.map { item ->
                        if (item.id == itemId) item.copy(isChecked = !item.isChecked) else item
                    }
                    note.copy(checklist = updatedChecklist)
                } else {
                    note
                }
            }
        }
    }

    override suspend fun duplicateNote(noteId: String) {
        _notesFlow.update { currentList ->
            val noteToDuplicate = currentList.find { it.id == noteId } ?: return@update currentList
            val duplicated = noteToDuplicate.copy(
                id = UUID.randomUUID().toString(),
                title = "${noteToDuplicate.title} (Copy)",
                updatedAt = System.currentTimeMillis(),
                dateFormatted = "Just now"
            )
            listOf(duplicated) + currentList
        }
    }

    override suspend fun getNoteById(id: String): Note? {
        return _notesFlow.value.find { it.id == id }
    }

    companion object {
        private fun createInitialNotes(): List<Note> {
            return emptyList()
//            return listOf(
//                Note(
//                    id = "pinned-1",
//                    title = "Weekly Grocery & Meal Plan",
//                    isPinned = true,
//                    category = "Personal",
//                    dateFormatted = "Edited 2h ago",
//                    checklist = listOf(
//                        ChecklistItem(id = "c1", text = "Sourdough boule & Greek yogurt", isChecked = true),
//                        ChecklistItem(id = "c2", text = "Organic baby spinach, avocados (3)", isChecked = false),
//                        ChecklistItem(id = "c3", text = "Wild salmon fillets & fresh dill", isChecked = false)
//                    )
//                ),
//                Note(
//                    id = "pinned-2",
//                    title = "Project Artemis Roadmap & Deadlines",
//                    body = "Sprint 04 starts Monday: finalize component parity, verify color contrast tokens, sync with core design...",
//                    isPinned = true,
//                    category = "Work • Artemis",
//                    attachmentsCount = 2,
//                    dateFormatted = "Updated Today, 9:42 AM"
//                ),
//                Note(
//                    id = "all-1",
//                    title = "Book\nRecommendati\nons",
//                    body = "The Psychology of Money, Atomic Habits, Slow Horses, Chip War,",
//                    isPinned = false,
//                    category = "Reading",
//                    dateFormatted = "Yesterday",
//                    cardIcon = "book"
//                ),
//                Note(
//                    id = "all-2",
//                    title = "Design System Principles",
//                    body = "Clarity, hierarchy, accessible contrast, typography rhythms,...",
//                    isPinned = false,
//                    category = "Design",
//                    dateFormatted = "Oct 24",
//                    cardIcon = "palette"
//                ),
//                Note(
//                    id = "all-3",
//                    title = "Apartment Hunting Checklist",
//                    body = "Saved 4 places near metro...",
//                    isPinned = false,
//                    category = "Life",
//                    dateFormatted = "Oct 20"
//                ),
//                Note(
//                    id = "all-4",
//                    title = "Meeting Highlights w/...",
//                    body = "Discussed Q4 metrics, revised sprint goals, team feedback, and...",
//                    isPinned = false,
//                    category = "Sync",
//                    dateFormatted = "Oct 18",
//                    cardIcon = "chat"
//                )
//            )

        }
    }
}