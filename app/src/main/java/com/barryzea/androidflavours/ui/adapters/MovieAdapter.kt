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

class MovieAdapter(private val onItemClick:(TmdbMovie)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listMovies:MutableList<TmdbMovie> = arrayListOf()
    private var isLoading = false
    private val VIEW_TYPE_MOVIE = 1
    private val VIEW_TYPE_LOADING = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false)
        val itemLoading = LayoutInflater.from(context).inflate(R.layout.item_loading,parent, false)
        return when(viewType){
            VIEW_TYPE_MOVIE->ViewHolder(itemView)
            else -> ViewHolderLoading(itemLoading)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position==listMovies.size - 1 && isLoading) VIEW_TYPE_LOADING else VIEW_TYPE_MOVIE
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = listMovies[position]
        when(holder.itemViewType){
            VIEW_TYPE_MOVIE->{
                (holder as ViewHolder).itemBind.apply{
                    ivMovie.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
                    tvTittle.text=movie.title
                    ratingBar.rating=(movie.voteAverage.toFloat() /2)
                    root.setOnClickListener { onItemClick(movie) }
                }
            }
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
    fun clear(){
        if(listMovies.isNotEmpty()){
            val size=listMovies.size
            listMovies.clear()
            notifyItemRangeRemoved(0,size)
        }
    }
    fun addLoadingItem(){
        isLoading = true
        listMovies.add(TmdbMovie())
    }
    fun removeLoadingItem(){
        if(listMovies.isNotEmpty()) {
            isLoading = false
            val position = listMovies.size - 1
            listMovies.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    override fun getItemCount()=listMovies.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val itemBind = ItemMovieBinding.bind(itemView)
    }
    inner class ViewHolderLoading(itemView: View):RecyclerView.ViewHolder(itemView)
}