package com.example.minichallenges.challenges.january.holidaymoviecollection.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity.Collection
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity.CollectionWithMovieCrossRef
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity.Movie

@Database(
    entities = [
        Movie::class,
        Collection::class,
        CollectionWithMovieCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieCollectionDatabase : RoomDatabase() {


    companion object {
        private var _dbInstance: MovieCollectionDatabase? = null

        fun getInstance(context: Context): MovieCollectionDatabase {
            if (_dbInstance == null) {
                synchronized(MovieCollectionDatabase::class) {
                    _dbInstance = Room.databaseBuilder(
                        context,
                        MovieCollectionDatabase::class.java,
                        "holiday_movie_collection_database.db"
                    ).build()
                }
            }
            return _dbInstance!!
        }

        fun getInstance() = _dbInstance!!
    }

    abstract val dao: MovieCollectionDao

}