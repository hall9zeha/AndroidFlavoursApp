package com.barryzea.androidflavours.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.navArgs
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.loadUrl
import com.barryzea.androidflavours.databinding.ActivityDetailBinding
import com.barryzea.androidflavours.ui.HomeFragment

class DetailActivity : AppCompatActivity() {

    private lateinit var bind:ActivityDetailBinding
    //Recuperamos los argumentos enviados desde HomeFragment
    val args:DetailActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpDetail()
    }
    private fun setUpDetail()= with(bind) {
        args.movie?.let {m ->
            ivMovieDetail.loadUrl("https://image.tmdb.org/t/p/w780${m.backdropPath}")
            tvMovieDetailDesc.text=m.overview
            toolbarDetail.title=m.title
            tvDetail.setMovieInfo(m)
        }
    }
}