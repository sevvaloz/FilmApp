package com.example.films

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.adapter.FilmAdapter
import com.example.films.databinding.ActivityMainBinding
import com.example.films.db.Film
import com.example.films.utils.RowClickListener
import com.example.films.viewmodel.FilmViewModel
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.job
import java.io.Serializable


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    val viewmodel: FilmViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: FilmAdapter
    var editActivityLauncher: ActivityResultLauncher<Intent>? = null
    var filmDetailsLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel.init(application)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        binding.rw.layoutManager = linearLayoutManager

        viewmodel.allFilms?.observe(this){filmList ->
            filmList.let {
                adapter = FilmAdapter(filmList, deleteListener, editListener, filmListener)
                binding.rw.adapter = adapter
            }
        }

        listener()
    }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this@MainActivity)

        return true
    }

    fun searchQuery(query: String){
        val sq = "%$query%"
        viewmodel.searchFilm(sq).observe(this){ filmList ->
            filmList.let{
                adapter = FilmAdapter(filmList, deleteListener, editListener, filmListener)
                binding.rw.adapter = adapter
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchQuery(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchQuery(query)
        }
        return true
    }

}


