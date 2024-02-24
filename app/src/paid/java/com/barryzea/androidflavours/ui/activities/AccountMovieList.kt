package com.barryzea.androidflavours.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.FAVORITES_LIST
import com.barryzea.androidflavours.common.WATCH_LIST
import com.barryzea.androidflavours.common.utils.PaginationRecyclerView
import com.barryzea.androidflavours.databinding.ActivityAccountMovieListBinding
import com.barryzea.androidflavours.domain.entities.DomainAuth
import com.barryzea.androidflavours.domain.entities.DomainMovie
import com.barryzea.androidflavours.ui.adapters.MovieAdapter
import com.barryzea.androidflavours.ui.viewmodel.AccountViewModel
import com.barryzea.androidflavours.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountMovieList : AppCompatActivity() {
    // Recuperamos los argumentos enviados desde UserAccountFragment
    private val args:AccountMovieListArgs by navArgs()
    private lateinit var bind: ActivityAccountMovieListBinding
    private var mAdapter:MovieAdapter?=null
    private val accountViewModel:AccountViewModel by viewModels()
    private val loginViewModel:LoginViewModel by viewModels()
    private lateinit var mLayoutManager: GridLayoutManager
    private var sessionId:String?=null
    private var mUser:DomainAuth?=null

    private var currentPage=1
    private var isLoading=false
    private var totalPages=0
    private var isLastPage=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityAccountMovieListBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpAdapter()
        setUpToolbar()
        setUpObservers()
        setUpPagination()
    }
    private fun setUpToolbar()=with(bind){
        setSupportActionBar(bind.toolbarDetail)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun setUpAdapter()=with(bind){
        mLayoutManager =GridLayoutManager(this@AccountMovieList,3)
        mAdapter = MovieAdapter {  }
        rvDetail.apply {
            setHasFixedSize(true)
            layoutManager=mLayoutManager
            adapter=mAdapter
        }
    }
    private fun setUpObservers(){
        loginViewModel.checkIfSessionIsCreated()
        accountViewModel.favoriteList.observe(this,::updateUi)
        loginViewModel.sessionIdPrefs.observe(this){sessionIdSaved->
            if(sessionIdSaved.isNotEmpty()){
                sessionId=sessionIdSaved
                loginViewModel.fetchUserDetail(sessionIdSaved)
            }
        }
        loginViewModel.userDetail.observe(this){user->
            mUser=user
            when(args.typeOfList){
                FAVORITES_LIST->{
                    supportActionBar?.title = getString(R.string.favorite_movies_subtitle)
                    accountViewModel.fetchMyFavoriteMovies(user.id.toString(),sessionId.toString(),1)}
                WATCH_LIST->{
                    supportActionBar?.title = getString(R.string.watchlist_movies_subtitle)
                }
            }

        }
    }
    private fun updateUi(domainMovie: DomainMovie?) {
        domainMovie?.let {
            bind.shimmerLoader.shimmerLoading.visibility=View.GONE
            if(isLoading && it.movies.isNotEmpty())mAdapter?.removeLoadingItem()
            totalPages=it.totalPages
            isLastPage=(currentPage >=totalPages)
            isLoading=false
            mAdapter?.addAll(it.movies)
        }
    }
    private fun setUpPagination(){
       bind.rvDetail.addOnScrollListener(object: PaginationRecyclerView(null,mLayoutManager){
            override fun loadMoreItems() {
                isLoading=true
                currentPage+=1
                mAdapter?.addLoadingItem()
                mUser?.let {
                    accountViewModel.fetchMyFavoriteMovies(it.id.toString(),sessionId.toString(),currentPage)
                }
            }
            override fun getTotalPageCount() = totalPages
            override fun isLastPage() = isLastPage
            override fun isLoading() = isLoading
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }

}