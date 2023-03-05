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

    @Query("SELECT * FROM $filmTable ORDER BY id DESC")
    fun getAllFilm(): LiveData<List<Film>>

    @Query("SELECT * FROM $filmTable WHERE name LIKE :searchQuery")
    fun searchFilm(searchQuery: String?): LiveData<List<Film>>

    @Query("SELECT * FROM $filmTable ORDER BY year DESC")
    fun sortFilmsDesc(): LiveData<List<Film>>

    @Query("SELECT * FROM $filmTable ORDER BY year ASC")
    fun sortFilmsAsc(): LiveData<List<Film>>

    @Update
    suspend fun updateFilm(film: Film)
}