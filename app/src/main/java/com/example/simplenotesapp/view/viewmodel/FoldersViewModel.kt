package com.example.simplenotesapp.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplefoldersapp.view.uimodel.FoldersScreenIntent
import com.example.simplenotesapp.model.Note
import com.example.simplenotesapp.repository.FolderRepository
import com.example.simplenotesapp.view.uimodel.FoldersScreenEffect
import com.example.simplenotesapp.view.uimodel.FoldersScreenViewState
import jakarta.inject.Inject
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

class FoldersViewModel @Inject constructor(
    private val repository: FolderRepository
) : ViewModel() {


    private var createFolderJob: Job? = null
    private val _viewState = MutableStateFlow(FoldersScreenViewState(isLoading = true))
    val viewState: StateFlow<FoldersScreenViewState> = _viewState.asStateFlow()

    private val _effects = Channel<FoldersScreenEffect>(Channel.BUFFERED)
    val effects: Flow<FoldersScreenEffect> = _effects.receiveAsFlow()

    private var recentlyDeletedFolder: Note? = null

    init {
        observeFolders()
    }

    private fun observeFolders() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }
            repository.getAllFolders()
                .catch { exception ->
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            userMessage = exception.localizedMessage ?: "Failed to load folders"
                        )
                    }
                }
                .collect { foldersList ->
                    _viewState.update {
                        it.copy(
                            folders = foldersList,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun handleIntent(intent: FoldersScreenIntent) {
        when (intent) {
            is FoldersScreenIntent.CreateQuickFolder -> {

                if (createFolderJob?.isActive == true) return
                try {
                    _viewState.update { it.copy(isLoading = true) }
                    createFolderJob = viewModelScope.launch {
                        repository.addFolder(intent.folder)
                        _effects.send(FoldersScreenEffect.ShowSnackbar("Folder created!"))
                        _viewState.update { it.copy(isLoading = false) }

                    }

                } catch (ex : Exception) {

                }

            }
            is FoldersScreenIntent.SearchFolder ->{
                _viewState.update { it.copy(isLoading = true) }
                _viewState.update { it.copy(searchQuery = intent.query) }
                _viewState.update { it.copy(isLoading = false) }
            }
            is FoldersScreenIntent.TogglePin ->{
                viewModelScope.launch {
                    repository.togglePin(intent.folderId)
                    _effects.send(FoldersScreenEffect.ShowSnackbar("Folder toggled pin!"))
                }
            }
            is FoldersScreenIntent.Deletefolder ->{
                _viewState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    repository.deleteFolder(intent.folderId)
                    _viewState.update { it.copy(isLoading = false) }
                }
            }
            is FoldersScreenIntent.SetActiveMenufolder ->{
                _viewState.update {
                    it.copy(activeMenuFolderId = intent.folderId)
                }
            }
            is FoldersScreenIntent.SetActiveTab ->{
                _viewState.update { it.copy(activeTab = intent.tab) }
            }
            else -> {

            }
        }
    }


}