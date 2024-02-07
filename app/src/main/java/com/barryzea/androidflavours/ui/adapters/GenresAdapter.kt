package com.barryzea.androidflavours.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.data.entities.Genre
import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.databinding.GenreItemBinding

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 07/02/2024.
 * Copyright (c)  All rights reserved.
 **/

class GenresAdapter:RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    private val genresList:MutableList<Genre> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.genre_item,parent, false)
        )
    }
    override fun getItemCount() = genresList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(genresList[position])
    }
    fun add(genres:List<Genre>){
        genres.forEach {genre->
            if(!genresList.contains(genre)){
                genresList.add(genre)
                notifyItemInserted(genresList.size-1)
            }
        }
    }
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        private val bindView = GenreItemBinding.bind(itemView)
        fun onBind(genre:Genre)= with(bindView){
            tvGenreTitle.text=genre.name
        }
    }
}