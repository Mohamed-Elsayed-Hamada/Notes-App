package com.example.simplenotesapp.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenotesapp.model.Note
import com.example.simplenotesapp.repository.NoteRepository
import com.example.simplenotesapp.view.uimodel.NotesScreenEffect
import com.example.simplenotesapp.view.uimodel.NotesScreenIntent
import com.example.simplenotesapp.view.uimodel.NotesScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesScreenViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    private var createNoteJob: Job? = null
    private val _viewState = MutableStateFlow(NotesScreenViewState(isLoading = true))
    val viewState: StateFlow<NotesScreenViewState> = _viewState.asStateFlow()

    private val _effects = Channel<NotesScreenEffect>(Channel.BUFFERED)
    val effects: Flow<NotesScreenEffect> = _effects.receiveAsFlow()

    private var recentlyDeletedNote: Note? = null

    init {
        observeNotes()
    }

    private fun observeNotes() {
        viewModelScope.launch {
            repository.getNotes()
                .catch { exception ->
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            userMessage = exception.localizedMessage ?: "Failed to load notes"
                        )
                    }
                }
                .collect { notesList ->
                    _viewState.update {
                        it.copy(
                            notes = notesList,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun handleIntent(intent: NotesScreenIntent) {
        when (intent) {
            is NotesScreenIntent.LoadNotes -> {
                // Handled reactively by observeNotes()
            }

            is NotesScreenIntent.SearchNotes -> {
                _viewState.update { it.copy(searchQuery = intent.query) }
            }

            is NotesScreenIntent.TogglePin -> {
                viewModelScope.launch {
                    repository.togglePin(intent.noteId)
                }
            }

            is NotesScreenIntent.NavigateToNoteDetails -> {
                viewModelScope.launch {
                    _effects.send(
                        NotesScreenEffect
                            .NavigateToDetail(intent.id)
                    )
                }
            }

            is NotesScreenIntent.DeleteNote -> {
                viewModelScope.launch {
                    val deleted = repository.deleteNote(intent.noteId)
                    recentlyDeletedNote = deleted
                    _viewState.update { it.copy(activeMenuNoteId = null) }
                    _effects.send(
                        NotesScreenEffect.ShowSnackbar(
                            message = "Note deleted",
                            actionLabel = "Undo"
                        )
                    )
                }
            }

            is NotesScreenIntent.UndoDelete -> {
                viewModelScope.launch {
                    recentlyDeletedNote?.let { note ->
                        repository.addNote(note)
                        recentlyDeletedNote = null
                    }
                }
            }

            is NotesScreenIntent.DuplicateNote -> {
                viewModelScope.launch {
                    repository.duplicateNote(intent.noteId)
                    _viewState.update { it.copy(activeMenuNoteId = null) }
                    _effects.send(NotesScreenEffect.ShowSnackbar("Note duplicated"))
                }
            }

            is NotesScreenIntent.ToggleChecklistItem -> {
                viewModelScope.launch {
                    repository.toggleChecklistItem(intent.noteId, intent.itemId)
                }
            }

            is NotesScreenIntent.SetActiveMenuNote -> {
                _viewState.update { it.copy(activeMenuNoteId = intent.noteId) }
            }

            is NotesScreenIntent.SetLayoutMode -> {
                _viewState.update { it.copy(isGridView = intent.isGrid) }
            }

            is NotesScreenIntent.SetSort -> {
                _viewState.update { it.copy(selectedSort = intent.sort) }
            }

            is NotesScreenIntent.SetActiveTab -> {
                _viewState.update { it.copy(activeTab = intent.tab) }
            }

            is NotesScreenIntent.CreateQuickNote -> {
                if (createNoteJob?.isActive == true) return
                try {
                    _viewState.update { it.copy(isLoading = true) }
                    createNoteJob = viewModelScope.launch {
//                        delay(1000L)
                        val quickNote = Note(
                            title = "Quick Note",
                            body = "Captured spontaneous thought...",
                            category = "Personal",
                            dateFormatted = "Just now"
                        )
                        repository.addNote(quickNote)
                        _effects.send(NotesScreenEffect.ShowSnackbar("Note created!"))
                        _viewState.update { it.copy(isLoading = false) }

                    }
                } catch (ex: Exception) {
                    _viewState.update { it.copy(isLoading = false) }

                } finally {

                }
            }

            is NotesScreenIntent.DismissMessage -> {
                _viewState.update { it.copy(userMessage = null) }
            }

            else -> {}
        }
    }
}