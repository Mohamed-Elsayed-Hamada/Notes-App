package com.example.simplenotesapp.view.uimodel

import com.example.simplenotesapp.model.Note

/**
 * Single source of truth for the Notes Screen UI in MVI architecture.
 */
data class NotesScreenViewState(
    val isLoading: Boolean = false,
    val notes: List<Note> = emptyList(),
    val isGridView: Boolean = true,
    val searchQuery: String = "",
    val selectedSort: String = "Recent",
    // "all-3" is open initially to match the screenshot mockup perfectly
    val activeMenuNoteId: String? = "all-3",
    val activeTab: String = "Notes",
    val userMessage: String? = null
) {
    val displayNotes: List<Note>
        get() = if (searchQuery.isBlank()) {
            notes
        } else {
            notes.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.body.contains(searchQuery, ignoreCase = true) ||
                        it.category.contains(searchQuery, ignoreCase = true)
            }
        }

    val pinnedNotes: List<Note>
        get() = displayNotes.filter { it.isPinned }

    val allNotes: List<Note>
        get() = displayNotes.filter { !it.isPinned }

    val totalNotesCount: Int
        get() = notes.size
//        get() = 12.coerceAtLeast(notes.size) // Shows 12 notes badge matching mockup
}

/**
 * State for Note editing/creation screen
 */
data class NoteDetailViewState(
    val isLoading: Boolean = false,
    val id: String? = null,
    val title: String = "",
    val body: String = "",
    val category: String = "Personal",
    val isPinned: Boolean = false,
    val titleError: String? = null
)