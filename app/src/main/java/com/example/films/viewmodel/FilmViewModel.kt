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

    private var _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private var _year = MutableLiveData<String>()
    val year: LiveData<String> get() = _year

    private var _director = MutableLiveData<String>()
    val director: LiveData<String> get() = _director

    private var _image = MutableLiveData<String>()
    val image: LiveData<String> get() = _image

    private var _subject = MutableLiveData<String>()
    val subject: LiveData<String> get() = _subject


    fun init(application: Application) {
        val dao = FilmDatabase.getDatabaseInstance(application).getFilmDao()
        repository = FilmRepository(dao)
        allFilms = repository?.allFilms
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


    fun setName(name: String){
        _name.value = name
    }

    fun setYear(year: String){
        _year.value = year
    }

    fun setDirector(director: String){
        _director.value = director
    }

    fun setImage(image: String){
        _image.value = image
    }

    fun setSubject(subject: String){
        _subject.value = subject
    }

}