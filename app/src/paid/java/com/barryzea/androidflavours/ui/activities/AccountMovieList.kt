package com.barryzea.androidflavours.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.barryzea.androidflavours.databinding.ActivityAccountMovieListBinding


class AccountMovieList : AppCompatActivity() {
    private lateinit var bind: ActivityAccountMovieListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityAccountMovieListBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}