package com.example.films.viewmodel

import androidx.lifecycle.LiveData
import com.example.films.db.Film
import com.example.films.db.FilmDao

class FilmRepository(private val filmDao: FilmDao) {

    val allFilms: LiveData<List<Film>> = filmDao.getAllFilm()

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