package com.barryzea.androidflavours.ui.activities

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.loadUrl
import com.barryzea.androidflavours.common.showSnackbar
import com.barryzea.androidflavours.databinding.ActivityDetailBinding
import com.barryzea.androidflavours.ui.adapters.CharacterAdapter
import com.barryzea.androidflavours.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var bind:ActivityDetailBinding
    //Recuperamos los argumentos enviados desde HomeFragment
    val args:DetailActivityArgs by navArgs()
    private val detailViewModel:DetailViewModel by viewModels()
    private var characterAdapter:CharacterAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpDetail()
        setUpAdapter()
        setUpObservers()

    }

    private fun setUpDetail()= with(bind) {
         val m = args.movie
        //args.movie?.let {m ->
        ivMovieDetail.loadUrl("https://image.tmdb.org/t/p/w780${m?.backdropPath}")
        ivPoster.loadUrl("https://image.tmdb.org/t/p/w780${m?.posterPath}")
        tvMovieDetailDesc.text=m?.overview
        toolbarDetail.title=m?.title
        //tvDetail.setMovieInfo(m!!)
        //}
        //nueva implementación
        tvLang.text=getString(R.string.original_language)  + m?.originalLanguage
        tvTitle.text=m?.originalTitle
        tvRelease.text = m?.releaseDate
        tvPopularity.text=getString(R.string.popularity) + m?.popularity.toString()
        ratingDetail.rating=m?.voteAverage!!.toFloat()
        //detailViewModel.fetchMovieCredits(m!!.id)
        detailViewModel.fetchCreditsAndTrailer(m!!.id)
    }
    private fun setUpAdapter(){
        characterAdapter= CharacterAdapter()
        bind.rvCharacters.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter = characterAdapter
        }
    }
    private fun setUpObservers(){
        detailViewModel.credits.observe(this){characters->
            characters?.let{
                characterAdapter?.add(characters)
            }
        }
        detailViewModel.infoMsg.observe(this){
            Log.e("DETALLE", it )
            bind.root.showSnackbar(it)
        }

    }

}