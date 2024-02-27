package com.barryzea.androidflavours.ui.activities

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.Toast
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
    private val args:DetailActivityArgs by navArgs()
    private val detailViewModel:DetailViewModel by viewModels()
    private var characterAdapter:CharacterAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpAdapter()
        setUpDetail()
        setUpListeners()
        setUpObservers()

    }

    private fun setUpDetail()= with(bind) {

         val m = args.movie
        //args.movie?.let {m ->
        ivMovieDetail.loadUrl("https://image.tmdb.org/t/p/w780${m?.backdropPath}")
        movInfo.ivPoster.loadUrl("https://image.tmdb.org/t/p/w780${m?.posterPath}")
        tvMovieDetailDesc.text=m?.overview
        toolbarDetail.title=m?.title
        //tvDetail.setMovieInfo(m!!)
        //}
        //nueva implementación
        movInfo.tvLang.text=getString(R.string.original_language)  + m?.originalLanguage
        movInfo.tvTitle.text=m?.originalTitle
        movInfo.tvRelease.text = m?.releaseDate
        movInfo.tvPopularity.text=getString(R.string.popularity) + m?.popularity.toString()
        movInfo.ratingDetail.rating=m?.voteAverage!!.toFloat()
        //detailViewModel.fetchMovieCredits(m!!.id)
        detailViewModel.fetchCreditsAndTrailer(m!!.id)

    }
    private fun setUpListeners() = with(bind){
        movInfo.btnBookmark.setOnClickListener {
            args.movie?.id?.let{
                detailViewModel.addToFavorite(it)

            }
        }
        movInfo.btnWatchList.setOnClickListener {
            args.movie?.id?.let{
                detailViewModel.addToWatchlist(it)
            }
        }
    }
    private fun setUpAdapter(){
        characterAdapter= CharacterAdapter()
        bind.body.rvCharacters.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter = characterAdapter
        }
    }
    private fun setUpObservers(){
        detailViewModel.isLogin.observe(this){
            if(it)bind.movInfo.lnAuthenticatedActions.visibility=View.VISIBLE

        }
        detailViewModel.credits.observe(this){characters->
            characters?.let{
                bind.body.rvCharacters.visibility=View.VISIBLE
                characterAdapter?.add(characters)

                //Al hacerce visible la vista que muestra el reparto de actores, empuja a las demás vistas hacia arriba
                //ya que es un scrollview el que las contiene, por lo tanto reseteamos su posición inmediatamente, esto es
                //prácticamente imperceptible
                bind.scrollViewMain.scrollTo(0,0)
            }
        }
        detailViewModel.infoMsg.observe(this){
            bind.root.showSnackbar(it)
        }
        detailViewModel.favoriteAdded.observe(this){
            Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()
        }
        detailViewModel.watchlistAdded.observe(this){
            Toast.makeText(this, "Added to watchlist", Toast.LENGTH_SHORT).show()
        }

    }

}