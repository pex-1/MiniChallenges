package com.example.minichallenges.challenges.january.holidaymoviecollection.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity.CollectionWithMovie
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.movies
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.MovieCollectionDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HolidayMovieCollectionViewModel : ViewModel() {

    private var _db: MovieCollectionDatabase? = null
    val db: MovieCollectionDatabase
        get() {
            if (_db == null) {
                throw IllegalStateException("Database instance is not initialized. Call initialize(context) first.")
            }
            return _db!!
        }

    private val _uiState: MutableStateFlow<MovieCollectionUiState> = MutableStateFlow(
        MovieCollectionUiState()
    )
    val uiState: StateFlow<MovieCollectionUiState> = _uiState.asStateFlow()

    fun onIntent(intent: MovieCollectionIntent) {
        viewModelScope.launch {
            when (intent) {
                is MovieCollectionIntent.FetchInitialData -> {
                    _db = MovieCollectionDatabase.getInstance(intent.context)
                    db.dao.insertMovies(
                        movies = movies
                    )
                    db.dao.getMovieCollections().collect { collections ->
                        _uiState.value = MovieCollectionUiState(
                            collections = collections
                        )
                    }
                }
            }
        }
    }
}

data class MovieCollectionUiState(
    val collections: List<CollectionWithMovie> = emptyList()
)

sealed interface MovieCollectionIntent {
    data class FetchInitialData(val context: Context) : MovieCollectionIntent
}

