package com.barryzea.androidflavours.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.loadUrl
import com.barryzea.androidflavours.data.entities.CharacterMovie
import com.barryzea.androidflavours.databinding.CharacterItemBinding

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 13/02/2024.
 **/

class CharacterAdapter:RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {
    private var charactersList:MutableList<CharacterMovie> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(charactersList[position])
    }
    fun add(characters:List<CharacterMovie>){
        characters.forEach {
            if(!charactersList.contains(it)){
                charactersList.add(it)
                notifyItemInserted(charactersList.size - 1)
            }
        }
    }
    override fun getItemCount()= charactersList.size
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
       private val bind = CharacterItemBinding.bind(itemView)
        fun onBind(character:CharacterMovie) = with(bind){
            ivCharacter.loadUrl("https://image.tmdb.org/t/p/w185${character.profilePath}")
            tvCharacterName.text=character.name
        }
    }
}