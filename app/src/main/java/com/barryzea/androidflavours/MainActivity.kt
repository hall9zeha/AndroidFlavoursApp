package com.barryzea.androidflavours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.barryzea.androidflavours.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.fetchMovies(1)
        viewModel.movies.observe(this){
            Log.e("MOVIES", it.movies.toString() )
        }
        viewModel.infoMsg.observe(this){
            Log.e("ERROR", it )
        }
    }
}