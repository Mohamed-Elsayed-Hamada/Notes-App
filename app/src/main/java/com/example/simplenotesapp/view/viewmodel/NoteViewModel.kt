package com.example.simplenotesapp.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenotesapp.model.Note
import com.example.simplenotesapp.repository.NoteRepository
import com.example.simplenotesapp.view.uimodel.NoteDetailIntent
import com.example.simplenotesapp.view.uimodel.NoteDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(NoteDetailViewState())
    val viewState: StateFlow<NoteDetailViewState> = _viewState.asStateFlow()

    fun handleIntent(intent: NoteDetailIntent) {
        when (intent) {
            is NoteDetailIntent.UpdateTitle -> {
                _viewState.update { it.copy(title = intent.title, titleError = null) }
            }
            is NoteDetailIntent.UpdateBody -> {
                _viewState.update { it.copy(body = intent.body) }
            }
            is NoteDetailIntent.UpdateCategory -> {
                _viewState.update { it.copy(category = intent.category) }
            }
            is NoteDetailIntent.TogglePin -> {
                _viewState.update { it.copy(isPinned = !it.isPinned) }
            }
            is NoteDetailIntent.SaveNote -> {
                saveNote()
            }
            is NoteDetailIntent.GetNoteDetails ->{
                viewModelScope.launch {
                    val note = repository.getNoteById( intent.id)
                    if(note != null){
                        _viewState.update {
                            it.copy(id = intent.id , title = note.title  ,
                                body = note.body , category = note.category,
                                isPinned = note.isPinned)
                        }
                    }
                }
            }
            is NoteDetailIntent.UpdateNote ->{
                viewModelScope.launch{
                    repository.updateNote(intent.note)
                }
            }
        }
    }

    private fun saveNote() {
        val currentState = _viewState.value
        if (currentState.title.isBlank()) {
            _viewState.update { it.copy(titleError = "Title cannot be empty") }
            return
        }

        _viewState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val note = Note(
                    id = currentState.id ?: java.util.UUID.randomUUID().toString(),
                    title = currentState.title,
                    body = currentState.body,
                    category = currentState.category,
                    isPinned = currentState.isPinned,
                    dateFormatted = "Just now"
                )
                if (currentState.id == null) {
                    repository.addNote(note)
                } else {
                    repository.updateNote(note)
                }
            } catch (ex: Exception) {
                // handle error
            } finally {
                _viewState.update { it.copy(isLoading = false) }
            }
        }
    }
}