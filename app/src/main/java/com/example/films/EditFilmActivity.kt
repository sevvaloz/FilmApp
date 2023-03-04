package com.example.films

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.films.databinding.ActivityAddFilmBinding
import com.example.films.databinding.ActivityEditFilmBinding
import com.example.films.db.Film
import com.example.films.utils.Utility
import com.example.films.viewmodel.FilmViewModel

class EditFilmActivity : AppCompatActivity() {

    private val viewmodel: FilmViewModel by viewModels()
    private lateinit var binding: ActivityEditFilmBinding

    lateinit var currentFilm: Film

    lateinit var nameTxt: String
    lateinit var yearTxt: String
    lateinit var directorTxt: String
    lateinit var imageTxt: String
    lateinit var subjectTxt: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_film)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_film)

        init()
        listener()
    }

    fun init(){
        currentFilm = intent.getSerializableExtra("FILM2") as Film
        binding.filmName.setText(currentFilm.name)
        binding.filmYear.setText(currentFilm.year)
        binding.filmDirector.setText(currentFilm.director)
        binding.filmImageUrl.setText(currentFilm.image)
        binding.filmSubject.setText(currentFilm.subject)
    }

    fun listener(){
        binding.editFilmBtn.setOnClickListener {
            if(!checkEditText()){
                val updatedFilm = Film(nameTxt, yearTxt, directorTxt, subjectTxt, imageTxt)
                updatedFilm.id = currentFilm.id
                viewmodel.updateFilm(updatedFilm)

                val intent =  Intent(this@EditFilmActivity, MainActivity::class.java)
                //intent.putExtra("FILM2", updatedFilm)
                startActivity(intent)
                //setResult(RESULT_OK, intent)
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

        return Utility.isNullOrEmpty(nameTxt) || Utility.isNullOrEmpty(yearTxt)
    }


}

