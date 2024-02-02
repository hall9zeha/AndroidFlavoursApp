package com.barryzea.androidflavours.ui.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.utils.PaginationRecyclerView
import com.barryzea.androidflavours.data.entities.TmdbResult
import com.barryzea.androidflavours.databinding.ActivityMainBinding
import com.barryzea.androidflavours.ui.adapters.MovieAdapter
import com.barryzea.androidflavours.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bind:ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController:NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpBottomNav()
    }
    private fun setUpBottomNav(){
        val navGraphIds = listOf(R.navigation.nav_graph)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
       navController = navHostFragment.navController
        val controller = bind.bottomNav.setupWithNavController(navController!!)
    }

}