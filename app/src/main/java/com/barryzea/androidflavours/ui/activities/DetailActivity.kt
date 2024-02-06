package com.barryzea.androidflavours.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.barryzea.androidflavours.common.loadUrl
import com.barryzea.androidflavours.databinding.ActivityDetailBinding

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
         val m = args.movie
        //args.movie?.let {m ->
            ivMovieDetail.loadUrl("https://image.tmdb.org/t/p/w780${m?.backdropPath}")
            tvMovieDetailDesc.text=m?.overview
            toolbarDetail.title=m?.title
            tvDetail.setMovieInfo(m!!)
        //}
    }
}