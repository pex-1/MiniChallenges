package com.example.minichallenges.challenges.january.holidaymoviecollection.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity.Movie
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.MovieCollectionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateCollectionViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<CreateCollectionUiState> = MutableStateFlow(
        CreateCollectionUiState()
    )
    val uiState: StateFlow<CreateCollectionUiState> = _uiState.onStart {
        getMovies()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _uiState.value
    )

    private val _sideEffect = MutableStateFlow<CreateCollectionSideEffect?>(null)
    val sideEffect = _sideEffect.asStateFlow()

    private val db by lazy {
        MovieCollectionDatabase.getInstance()
    }

    private fun getMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val movies = db.dao.getAllMovies()
                _uiState.value = _uiState.value.copy(allMovies = movies, isLoading = false)
            }
        }
    }

    fun onIntent(intent: CreateCollectionIntent) {
        viewModelScope.launch {
            when (intent) {
                is CreateCollectionIntent.AddMovieToCollection -> {
                    val selectedMovies = _uiState.value.selectedMovies.toMutableList()
                    selectedMovies.add(intent.movie)
                    _uiState.value = _uiState.value.copy(selectedMovies = selectedMovies)
                }

                is CreateCollectionIntent.RemoveMovieFromCollection -> {
                    val selectedMovies = _uiState.value.selectedMovies.toMutableList()
                    selectedMovies.remove(intent.movie)
                    _uiState.value = _uiState.value.copy(selectedMovies = selectedMovies)
                }

                is CreateCollectionIntent.CreateCollection -> {
                    withContext(Dispatchers.IO) {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                        val collectionId = db.dao.createCollection(_uiState.value.collectionName)
                        _uiState.value.selectedMovies.forEach { movie ->
                            db.dao.addMovieToCollection(collectionId, movie.movieId)
                        }
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        _sideEffect.value = CreateCollectionSideEffect.OnCollectionCreated(collectionId)
                    }
                }

                is CreateCollectionIntent.UpdateCollectionName -> {
                    _uiState.value = _uiState.value.copy(collectionName = intent.name.let {
                        if (it.length > 40) {
                            it.substring(0, 40)
                        } else {
                            it
                        }
                    })
                }
            }
        }
    }

}

data class CreateCollectionUiState(
    val collectionName: String = "",
    val allMovies: List<Movie> = emptyList(),
    val selectedMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface CreateCollectionIntent {
    data class AddMovieToCollection(val movie: Movie) : CreateCollectionIntent
    data class RemoveMovieFromCollection(val movie: Movie) : CreateCollectionIntent
    data class UpdateCollectionName(val name: String) : CreateCollectionIntent
    data object CreateCollection : CreateCollectionIntent
}

sealed interface CreateCollectionSideEffect {
    data class OnCollectionCreated(val collectionId: Long) : CreateCollectionSideEffect
}