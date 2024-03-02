package com.barryzea.androidflavours.ui.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.getYoutubeThumbnailPath
import com.barryzea.androidflavours.common.getYoutubeVideoPath
import com.barryzea.androidflavours.common.loadUrl
import com.barryzea.androidflavours.data.entities.TrailerMovie
import com.barryzea.androidflavours.data.entities.Trailers
import com.barryzea.androidflavours.databinding.ItemVideoBinding

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 02/03/2024.
 **/

class VideoAdapter:RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    private var trailersList:MutableList<TrailerMovie> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_video,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(trailersList[position])
    }

    override fun getItemCount(): Int = trailersList.size
    fun addAll(trailers:List<TrailerMovie>){
        trailers.forEach {trailer->
            if(!trailersList.contains(trailer)){
                trailersList.add(trailer)
                notifyItemInserted(trailersList.size-1)
            }
        }
    }
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val bind = ItemVideoBinding.bind(itemView)
        fun onBind(trailer:TrailerMovie) = with(bind){
            itemVideoCover.loadUrl(getYoutubeThumbnailPath(trailer.key.toString()))
            root.setOnClickListener {
                root.context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(getYoutubeVideoPath(trailer.key.toString())))
                )
            }
        }
    }
}