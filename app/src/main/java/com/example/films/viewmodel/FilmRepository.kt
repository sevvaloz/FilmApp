package com.example.films.viewmodel

import androidx.lifecycle.LiveData
import com.example.films.db.Film
import com.example.films.db.FilmDao
import kotlinx.coroutines.flow.Flow

class FilmRepository(private val filmDao: FilmDao) {

    val allFilms: LiveData<List<Film>> = filmDao.getAllFilm()

     fun searchFilm(searchQuery: String): LiveData<List<Film>> {
        return filmDao.searchFilm(searchQuery)
    }

    suspend fun insertFilm(film: Film){
        filmDao.insertFilm(film)
    }

    suspend fun deleteFilm(film: Film) {
        filmDao.deleteFilm(film)
    }

    suspend fun updateFilm(film: Film) {
        filmDao.updateFilm(film)
    }
}