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
 * Created by Barry Zea H. on 20/02/2024.
 **/

class AccountMoviesAdapter(private val onItemClick:(TmdbMovie)->Unit):RecyclerView.Adapter<AccountMoviesAdapter.ViewHolder>() {
    private var moviesList:MutableList<TmdbMovie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false)
        return ViewHolder(itemView)
    }
    fun addAll(movies:List<TmdbMovie>){
        movies.forEach {movie->
            if(!moviesList.contains(movie)){
                moviesList.add(movie)
                notifyItemInserted(moviesList.size-1)
            }
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(moviesList[position])
    }

    override fun getItemCount() = moviesList.size
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val bind=ItemMovieBinding.bind(itemView)
        fun onBind(movie:TmdbMovie)=with(bind){
            ivMovie.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
            tvTittle.text=movie.title
            ratingBar.rating=(movie.voteAverage.toFloat() /2)
            root.setOnClickListener { onItemClick(movie) }
        }
    }
}