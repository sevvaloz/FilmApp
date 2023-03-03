package com.example.films

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import com.example.films.databinding.ActivityAddFilmBinding
import com.example.films.db.Film
import com.example.films.utils.Utility
import com.example.films.viewmodel.FilmViewModel

class EditFilmActivity : AppCompatActivity() {

    val viewmodel: FilmViewModel by viewModels()
    var binding: ActivityAddFilmBinding? = null

    lateinit var currentFilm: Film

    lateinit var nameTxt: String
    lateinit var yearTxt: String
    lateinit var directorTxt: String
    lateinit var imageTxt: String
    lateinit var subjectTxt: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_film)

        init()
        listener()
    }

    fun init(){
        currentFilm = intent.getSerializableExtra("FILM2") as Film
        binding?.filmName?.setText(currentFilm.name)
        binding?.filmYear?.setText(currentFilm.year)
        binding?.filmDirector?.setText(currentFilm.director)
        binding?.filmImageUrl?.setText(currentFilm.image)
        binding?.filmSubject?.setText(currentFilm.subject)
    }

    fun listener(){
        binding?.addFilmBtn?.setOnClickListener {
            if(!checkEditText()){
                val updatedFilm = Film(nameTxt, yearTxt, directorTxt, subjectTxt, imageTxt)
                updatedFilm.id = currentFilm.id
                viewmodel.updateFilm(updatedFilm)

                val intent =  Intent(this@EditFilmActivity, MainActivity::class.java)
                intent.putExtra("FILM", updatedFilm)
                //startActivity(intent)
                setResult(RESULT_OK, intent)
            }
            else{
                //show error
            }
        }
    }

    fun checkEditText(): Boolean{
        nameTxt = binding?.filmName?.text.toString()
        yearTxt = binding?.filmYear?.text.toString()
        directorTxt = binding?.filmDirector?.text.toString()
        imageTxt = binding?.filmImageUrl?.text.toString()
        subjectTxt = binding?.filmSubject?.text.toString()

        return Utility.isNullOrEmpty(nameTxt) || Utility.isNullOrEmpty(yearTxt)
    }

    companion object{
        fun create(context: Context, film: Film) = Intent(context, EditFilmActivity::class.java).putExtra("FILM", film)
    }

}

