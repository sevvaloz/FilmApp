package com.example.films.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.films.db.Film
import com.example.films.db.FilmDao
import kotlinx.coroutines.flow.Flow

class FilmRepository(private val filmDao: FilmDao) {

    val allFilms: LiveData<List<Film>> = filmDao.getAllFilm()

     fun searchFilm(searchQuery: String): LiveData<List<Film>> {
        return filmDao.searchFilm(searchQuery)
    }

    val sortedFilmsDesc: LiveData<List<Film>> = filmDao.sortFilmsDesc()

    val sortedFilmsAsc: LiveData<List<Film>> = filmDao.sortFilmsAsc()

    suspend fun insertFilm(film: Film){
        filmDao.insertFilm(film)
    }

    suspend fun deleteFilm(film: Film) {
        filmDao.deleteFilm(film)
    }

    suspend fun updateFilm(film: Film) {
        try {
            filmDao.updateFilm(film)
        } catch (e: java.lang.Exception) {
            Log.d("movieError", e.message!!)
        }
    }
}