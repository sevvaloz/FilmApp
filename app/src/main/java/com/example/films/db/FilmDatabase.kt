package com.example.films.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Film::class],
    version = 1,
    exportSchema = false
)

abstract class FilmDatabase: RoomDatabase() {

    abstract fun getFilmDao(): FilmDao

    companion object{
        @Volatile
        private var instance: FilmDatabase? = null

        fun getDatabaseInstance(context: Context): FilmDatabase{
            return instance ?: synchronized(this){

                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    FilmDatabase::class.java,
                    "film_database"
                ).build()

                instance = newInstance
                newInstance
            }
        }
    }
}