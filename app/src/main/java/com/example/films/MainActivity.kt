package com.example.films

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.adapter.FilmAdapter
import com.example.films.databinding.ActivityMainBinding
import com.example.films.db.Film
import com.example.films.utils.RowClickListener
import com.example.films.viewmodel.FilmViewModel
import java.io.Serializable


class MainActivity : AppCompatActivity() {

    val viewmodel: FilmViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    var editActivityLauncher: ActivityResultLauncher<Intent>? = null
    var filmDetailsLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel.init(application)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        init()
        listener()

    }

    private fun init(){

        val deleteListener = object : RowClickListener<Serializable>{
            override fun onRowClick(row: Int, item: Serializable) {
                val film = item as Film
                showDeleteMessage(this@MainActivity, film)
            }
        }

        val editListener = object : RowClickListener<Serializable>{
            override fun onRowClick(row: Int, item: Serializable) {
                val film = item as Film
                //editActivityLauncher?.launch(EditFilmActivity.create(this@MainActivity, film))
                val intent = Intent(this@MainActivity, EditFilmActivity::class.java).putExtra("FILM2", film)
                startActivity(intent)
            }
        }

        val filmListener = object : RowClickListener<Serializable> {
            override fun onRowClick(row: Int, item: Serializable) {
                val film = item as Film
                //filmDetailsLauncher?.launch(FilmDetailsActivity.create(this@MainActivity, film))
                val intent = Intent(this@MainActivity, FilmDetailsActivity::class.java).putExtra("FILM", film)
                startActivity(intent)
            }
        }

        viewmodel.allFilms?.observe(this){filmList ->
            filmList.let {
                binding.rw.adapter = FilmAdapter(filmList, deleteListener, editListener, filmListener)
                binding.rw.layoutManager = LinearLayoutManager(this)
            }
        }

    }

    fun listener(){
        binding.addBtn.setOnClickListener {
            val intent =  Intent(applicationContext, AddFilmActivity::class.java)
            startActivity(intent)
        }
    }

    fun showDeleteMessage(context: Context, film: Film){
        val builder = AlertDialog.Builder(context)
        val alert: AlertDialog? = builder
            .setMessage("Do you want to delete ${film.name}?")
            .setPositiveButton("Yes"){ it, _ ->
                viewmodel.deleteFilm(film)
                it.dismiss()
            }
            .setNegativeButton("No"){ it, _ ->
                it.dismiss()
            }
            .create()
        alert?.show()
    }
}


