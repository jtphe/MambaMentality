package com.example.mambamentality.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mambamentality.R
import com.example.mambamentality.models.Movie
import kotlinx.android.synthetic.main.mamba_movie_item_list.view.*

class MambaMovieAdapter(private val list: List<Movie>) :
    RecyclerView.Adapter<MambaMovieAdapter.MambaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MambaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mamba_movie_item_list, parent,false)
        return MambaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MambaViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int =
        list.size


    inner class MambaViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            Log.d("movie", movie.poster_path.toString())
            view.movie_movie_title.text = movie.title
            val uri: Uri = Uri.parse("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
            val draweeView = view.movie_image_view

            draweeView.setImageURI(uri)
        }
    }
}