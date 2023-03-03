package com.example.films

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.films.databinding.ActivityFilmDetailsBinding
import com.example.films.db.Film
import com.example.films.utils.Utility
import com.example.films.viewmodel.FilmViewModel

class FilmDetailsActivity : AppCompatActivity() {

    private val vm by viewModels<FilmViewModel>()
    private var binding: ActivityFilmDetailsBinding? = null
    lateinit var currentFilm: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_details)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_film_details)
        binding!!.viewmodel = vm
        binding!!.lifecycleOwner = this


        vm.allFilms?.observe(this){filmList ->
            currentFilm = intent.getSerializableExtra("FILM") as Film
            filmList.let{
                vm.setName(currentFilm.name)
                vm.setYear(currentFilm.year)
                vm.setDirector(currentFilm.director)
                vm.setImage(currentFilm.image)
                vm.setSubject(currentFilm.subject)
            }
        }
    }

    companion object{
        fun create(context: Context, film: Film) = Intent(context, FilmDetailsActivity::class.java).putExtra("FILM", film)
    }

}