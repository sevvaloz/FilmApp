package com.example.films

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.films.databinding.ActivityAddFilmBinding
import com.example.films.db.Film
import com.example.films.utils.Utility.Companion.isNullOrEmpty
import com.example.films.viewmodel.FilmViewModel


class AddFilmActivity : AppCompatActivity() {

    val viewmodel: FilmViewModel by viewModels()
    lateinit var binding: ActivityAddFilmBinding

    lateinit var currentFilm: Film

    lateinit var nameTxt: String
    lateinit var yearTxt: String
    lateinit var directorTxt: String
    lateinit var imageTxt: String
    lateinit var subjectTxt: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_film)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_film)
        viewmodel.init(application)

        listener()
    }

    fun listener(){
        binding.addFilmBtn.setOnClickListener {
            if(!checkEditText()){
                val newFilm = Film(nameTxt, yearTxt, directorTxt, subjectTxt, imageTxt)
                viewmodel.addFilm(newFilm)

                val intent =  Intent(applicationContext, MainActivity::class.java)
                //intent.putExtra("FILM", newFilm)
                startActivity(intent)
            }
            else{
                //show error
            }
        }
    }

    fun checkEditText(): Boolean{
        nameTxt = binding.filmName.text.toString()
        yearTxt = binding.filmYear.text.toString()
        directorTxt = binding.filmDirector.text.toString()
        imageTxt = binding.filmImageUrl.text.toString()
        subjectTxt = binding.filmSubject.text.toString()

        return isNullOrEmpty(nameTxt) || isNullOrEmpty(yearTxt)
    }

}

