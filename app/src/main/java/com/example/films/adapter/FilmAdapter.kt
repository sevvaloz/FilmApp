package com.example.films.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.databinding.RecyclerviewRowBinding
import com.example.films.db.Film
import com.example.films.utils.RowClickListener
import com.example.films.utils.dataBindingAdapter
import com.example.films.viewmodel.FilmViewModel
import java.io.Serializable


class FilmAdapter(
    val filmList: List<Film>,
    val deleteClickListener: RowClickListener<Serializable>,
    val editClickListener: RowClickListener<Serializable>,
    val filmClickListener: RowClickListener<Serializable>
    ): RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    class FilmViewHolder(val binding: RecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: RecyclerviewRowBinding by dataBindingAdapter(R.layout.recyclerview_row, inflater, parent)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val currentFilm = filmList[position]
        holder.binding.filmNameTxt.text = currentFilm.name
        holder.binding.filmYearTxt.text = currentFilm.year
        Glide.with(holder.binding.root)
            .load(currentFilm.image)
            .circleCrop()
            .fitCenter()
            .into(holder.binding.filmImage)

        holder.binding.deleteBtn.setOnClickListener {
            deleteClickListener.onRowClick(position, currentFilm)
        }
        holder.binding.editBtn.setOnClickListener {
            editClickListener.onRowClick(position, currentFilm)
        }
        holder.binding.cardview.setOnClickListener {
            filmClickListener.onRowClick(position, currentFilm)
        }

    }

    override fun getItemCount(): Int = filmList.size

}