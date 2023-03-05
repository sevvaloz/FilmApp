package com.example.films

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.adapter.FilmAdapter
import com.example.films.databinding.ActivityMainBinding
import com.example.films.db.Film
import com.example.films.utils.RowClickListener
import com.example.films.viewmodel.FilmViewModel
import java.io.Serializable


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    val viewmodel: FilmViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: FilmAdapter
    var filmlist = ArrayList<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel.init(application)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.rw.layoutManager = LinearLayoutManager(this)

        listener()
        observeViewmodel()
    }

    fun observeViewmodel(){
        viewmodel.allFilms?.observe(this){filmList ->
            filmList.let {
                adapter = FilmAdapter(filmList, deleteListener, editListener, filmListener)
                binding.rw.adapter = adapter
            }
        }
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
                val intent = Intent(this@MainActivity, EditFilmActivity::class.java).putExtra("FILM2", film)
                startActivity(intent)
            }
        }

        val filmListener = object : RowClickListener<Serializable> {
            override fun onRowClick(row: Int, item: Serializable) {
                val film = item as Film
                //val intent = Intent(this@MainActivity, FilmDetailsActivity::class.java).putExtra("FILM", film)
                //startActivity(intent)
                FilmDetailsActivity.start(this@MainActivity, film)
            }
        }

    fun listener(){
        binding.addBtn.setOnClickListener {
            val intent =  Intent(applicationContext, AddFilmActivity::class.java)
            startActivity(intent)
        }

        binding.up.setOnClickListener {
            viewmodel.sortedFilmsDesc?.observe(this){filmList ->
                filmList.let {
                    adapter = FilmAdapter(filmList, deleteListener, editListener, filmListener)
                    binding.rw.adapter = adapter
                }

            }
        }

        binding.down.setOnClickListener {
            viewmodel.sortedFilmsAsc?.observe(this){filmList ->
                filmList.let {
                    adapter = FilmAdapter(filmList, deleteListener, editListener, filmListener)
                    binding.rw.adapter = adapter
                }

            }
        }

        binding.editext.doAfterTextChanged {text ->
            filmlist.filter { it.name.contains(text.toString()) }
        }

        //val str: String = "string"
        //str.isname()
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

    //search film
    fun searchQuery(query: String){
        val sq = "%$query%"
        viewmodel.searchFilm(sq).observe(this){filmList ->
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


