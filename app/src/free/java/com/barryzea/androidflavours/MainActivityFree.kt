package com.barryzea.androidflavours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.barryzea.androidflavours.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityFree : AppCompatActivity() {
    private val viewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_free)
        viewModel.fetchMovies(1)
        viewModel.movies.observe(this){
            Log.e("MOVIES-FREE_FLAV", it.movies.toString() )
        }
        viewModel.infoMsg.observe(this){
            Log.e("ERROR-FREE-FLAV", it )
        }
    }
}