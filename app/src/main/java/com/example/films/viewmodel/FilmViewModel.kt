package com.example.films.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.films.db.Film
import com.example.films.db.FilmDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FilmViewModel(): ViewModel() {

   var allFilms : LiveData<List<Film>>? = null
   var repository : FilmRepository? = null
    var sortedFilmsDesc: LiveData<List<Film>>? = null
    var sortedFilmsAsc: LiveData<List<Film>>? = null


    fun init(application: Application) {
        val dao = FilmDatabase.getDatabaseInstance(application).getFilmDao()
        repository = FilmRepository(dao)
        allFilms = repository?.allFilms
        sortedFilmsDesc = repository?.sortedFilmsDesc
        sortedFilmsAsc = repository?.sortedFilmsAsc
    }

    fun addFilm(film: Film) = viewModelScope.launch(Dispatchers.IO){
        repository?.insertFilm(film)
    }

    fun deleteFilm(film: Film) = viewModelScope.launch(Dispatchers.IO) {
        repository?.deleteFilm(film)
    }

    fun updateFilm(film: Film) = viewModelScope.launch(Dispatchers.IO) {
        repository?.updateFilm(film)
    }

     fun searchFilm(searchQuery: String): LiveData<List<Film>> {
        return repository?.searchFilm(searchQuery) as LiveData
    }
}