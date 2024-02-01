package com.barryzea.androidflavours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.barryzea.androidflavours.databinding.ActivityMainBinding
import com.barryzea.androidflavours.ui.adapters.MovieAdapter
import com.barryzea.androidflavours.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bind:ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private  var movieAdapter:MovieAdapter?=null
    private lateinit var mLayoutManager:GridLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpAdapter()
        viewModel.fetchMovies(1)
        viewModel.movies.observe(this){
            movieAdapter?.addAll(it.movies)
        }
        viewModel.infoMsg.observe(this){
            Log.e("ERROR", it )
        }
    }
    private fun setUpAdapter(){
        movieAdapter = MovieAdapter()
        mLayoutManager=GridLayoutManager(this@MainActivity,3)
        bind.rvMovies.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            adapter=movieAdapter
        }
    }
}