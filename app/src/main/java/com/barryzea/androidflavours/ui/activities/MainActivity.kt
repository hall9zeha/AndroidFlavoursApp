package com.barryzea.androidflavours.ui.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
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
    private  var movieAdapter:MovieAdapter?=null
    private lateinit var mLayoutManager:GridLayoutManager
    private var currentPage=1
    private var isLoading=false
    private var totalPages=0
    private var isLastPage=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpShimmerLayout(true)
        setUpAdapter()
        setUpPagination()
        viewModel.fetchMovies(1)
        viewModel.movies.observe(this, Observer(::updateUi))
        viewModel.infoMsg.observe(this){
            Log.e("ERROR", it )
        }
    }

    private fun updateUi(tmdbResult: TmdbResult?) {
        tmdbResult?.let {
            setUpShimmerLayout(false)
            if(isLoading && it.movies.isNotEmpty())movieAdapter?.removeLoadingItem()
            totalPages=it.total_pages
            isLastPage=(currentPage >=totalPages)
            isLoading=false
            movieAdapter?.addAll(it.movies)

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
    private fun setUpShimmerLayout(enable:Boolean){
        if(enable){
            bind.shimmerLoading.shimmerLoading.startShimmer()
        }else{
            bind.shimmerLoading.shimmerLoading.stopShimmer()
            bind.shimmerLoading.shimmerLoading.visibility = View.GONE
        }
    }
    private fun setUpPagination(){
        bind.rvMovies.addOnScrollListener(object:PaginationRecyclerView(mLayoutManager){
            override fun loadMoreItems() {
                isLoading=true
                currentPage+=1
                movieAdapter?.addLoadingItem()
                viewModel.fetchMovies(currentPage)
            }

            override fun getTotalPageCount() = totalPages

            override fun isLastPage() = isLastPage

            override fun isLoading() = isLoading
        })
    }
}