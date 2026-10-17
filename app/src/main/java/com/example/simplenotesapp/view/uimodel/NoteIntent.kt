package com.example.simplenotesapp.view.uimodel

/**
 * Intents representing user actions or system events on NotesScreen.
 */
sealed interface NotesScreenIntent {
    data object LoadNotes : NotesScreenIntent
    data class SearchNotes(val query: String) : NotesScreenIntent
    data class TogglePin(val noteId: String) : NotesScreenIntent
    data class DeleteNote(val noteId: String) : NotesScreenIntent
    data class DuplicateNote(val noteId: String) : NotesScreenIntent
    data class ToggleChecklistItem(val noteId: String, val itemId: String) : NotesScreenIntent
    data class SetActiveMenuNote(val noteId: String?) : NotesScreenIntent
    data class SetLayoutMode(val isGrid: Boolean) : NotesScreenIntent
    data class SetSort(val sort: String) : NotesScreenIntent    // ----- ask about it
    data class SetActiveTab(val tab: String) : NotesScreenIntent // ----- ask about it
    data object UndoDelete : NotesScreenIntent
    data object CreateQuickNote : NotesScreenIntent
    data object DismissMessage : NotesScreenIntent
    
    data class NavigateToNoteDetails(val id: String) : NotesScreenIntent
}

/**
 * One-off side effects emitted by the ViewModel (e.g. snackbars, navigation)
 */
sealed interface NotesScreenEffect {
    data class ShowSnackbar(val message: String, val actionLabel: String? = null) :
        NotesScreenEffect

    data class NavigateToDetail(val noteId: String?) : NotesScreenEffect
}

/**
 * Intents for Note Detail/Editor
 */
sealed interface NoteDetailIntent {
    data class UpdateTitle(val title: String) : NoteDetailIntent
    data class UpdateBody(val body: String) : NoteDetailIntent
    data class UpdateCategory(val category: String) : NoteDetailIntent
    data object TogglePin : NoteDetailIntent
    data object SaveNote : NoteDetailIntent
}