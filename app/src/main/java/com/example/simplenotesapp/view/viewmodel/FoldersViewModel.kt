package com.example.simplenotesapp.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.simplenotesapp.model.Note
import com.example.simplenotesapp.repository.FolderRepository
import com.example.simplenotesapp.view.uimodel.FoldersScreenViewState
import com.example.simplenotesapp.view.uimodel.NotesScreenEffect
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FoldersViewModel @Inject constructor(
    private val repository: FolderRepository): ViewModel() {


    private var createNoteJob: Job? = null
    private val _viewState = MutableStateFlow(FoldersScreenViewState(isLoading = true))
    val viewState: StateFlow<FoldersScreenViewState> = _viewState.asStateFlow()

    private val _effects = Channel<NotesScreenEffect>(Channel.BUFFERED)
    val effects: Flow<NotesScreenEffect> = _effects.receiveAsFlow()

    private var recentlyDeletedNote: Note? = null

}