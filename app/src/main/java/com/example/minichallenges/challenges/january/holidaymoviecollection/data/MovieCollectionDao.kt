package com.example.minichallenges.challenges.january.holidaymoviecollection.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity.CollectionWithMovie
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieCollectionDao {

    @Transaction
    @Query("SELECT * FROM collections")
    fun getMovieCollections(): Flow<List<CollectionWithMovie>>

    @Query("INSERT INTO collections (name) VALUES (:collectionName)")
    suspend fun createCollection(collectionName: String): Long

    @Query("INSERT INTO playlist_song_cross_ref (collectionId, movieId) VALUES (:collectionId, :movieId)")
    suspend fun addMovieToCollection(collectionId: Long, movieId: Long)

    @Upsert
    suspend fun insertMovie(movie: Movie)

    @Transaction
    suspend fun insertMovies(movies: List<Movie>) {
        movies.forEach { movie ->
            insertMovie(movie)
        }
    }

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<Movie>

}