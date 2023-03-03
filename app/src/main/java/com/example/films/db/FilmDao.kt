package com.example.films.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.films.utils.Constants.Companion.filmTable

@Dao
interface FilmDao {
    @Insert
    suspend fun insertFilm(film:Film)

    @Delete
    suspend fun deleteFilm(film:Film)

    @Query("Select * from $filmTable order by id ASC")
    fun getAllFilm(): LiveData<List<Film>>

    @Update
    suspend fun updateFilm(film: Film)
}