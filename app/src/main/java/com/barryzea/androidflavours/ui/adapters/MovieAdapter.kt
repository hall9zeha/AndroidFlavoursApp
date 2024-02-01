package com.barryzea.androidflavours.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.loadUrl
import com.barryzea.androidflavours.data.entities.TmdbMovie
import com.barryzea.androidflavours.databinding.ItemMovieBinding

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 01/02/2024.
 **/

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var listMovies:MutableList<TmdbMovie> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.itemBind.apply{
            ivMovie.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
        }
    }
    fun addAll(movies:List<TmdbMovie>){
        movies.forEach { mov->
            if(!listMovies.contains(mov)){
                listMovies.add(mov)
                notifyItemInserted(listMovies.size -1)
            }
        }
    }
    override fun getItemCount()=listMovies.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val itemBind = ItemMovieBinding.bind(itemView)
    }
}