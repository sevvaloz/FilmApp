package com.example.films

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.films.databinding.ActivityEditFilmBinding
import com.example.films.databinding.ActivityFilmDetailsBinding
import com.example.films.db.Film
import com.example.films.utils.Utility
import com.example.films.viewmodel.FilmViewModel

class FilmDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilmDetailsBinding
    lateinit var currentFilm: Film

    companion object{
        @JvmStatic
        fun start(context: Context, film: Film) = with(context){
            Intent(this, FilmDetailsActivity::class.java).putExtra("FILM", film).let {
                context.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_details)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_film_details)

        init()
    }

    fun init(){
        currentFilm = intent.getSerializableExtra("FILM") as Film
        binding.filmName.text = currentFilm.name
        binding.filmYear.text = currentFilm.year
        binding.filmDirector.text = currentFilm.director
        binding.filmImageUrl.text = currentFilm.image
        binding.filmSubject.text = currentFilm.subject
    }
}