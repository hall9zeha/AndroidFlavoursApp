package com.barryzea.androidflavours.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.FAVORITES_LIST
import com.barryzea.androidflavours.common.WATCH_LIST
import com.barryzea.androidflavours.common.utils.PaginationRecyclerView
import com.barryzea.androidflavours.databinding.FragmentAccountMovieListBinding
import com.barryzea.androidflavours.domain.entities.DomainAuth
import com.barryzea.androidflavours.domain.entities.DomainMovie
import com.barryzea.androidflavours.ui.activities.MainActivity
import com.barryzea.androidflavours.ui.adapters.MovieAdapter
import com.barryzea.androidflavours.ui.viewmodel.AccountViewModel
import com.barryzea.androidflavours.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountMovieListFragment : Fragment() {
    // Recuperamos los argumentos enviados desde UserAccountFragment
    private val args:AccountMovieListFragmentArgs by navArgs()
    private  var _bind: FragmentAccountMovieListBinding?=null
    private val bind:FragmentAccountMovieListBinding get() = _bind!!
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

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let{
            _bind= FragmentAccountMovieListBinding.inflate(inflater,container,false)
            _bind?.let{
                return it.root
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onMenuItemSelected()
        setUpAdapter()
        setUpToolbar()
        setUpListeners()
        setUpObservers()
        setUpPagination()
    }
    /*
    * Le agregamos un toolbar a la MainActivity que es el contenedor principal, solamente cuando se muestre nuestro fragmento
    * AccountMovieListFragment
    * */
    private fun setUpToolbar()=with(bind){
        (activity as? MainActivity)?.bind?.ctlHeader?.visibility = View.GONE
        (requireActivity() as AppCompatActivity).apply {

            setSupportActionBar(bind.toolbarDetail)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
    private fun setUpAdapter()=with(bind){
        mLayoutManager =GridLayoutManager(context,3)
        mAdapter = MovieAdapter {
            val action = AccountMovieListFragmentDirections.actionGoToDetail(it)
            Navigation.findNavController(bind.root).navigate(action)
        }
        rvDetail.apply {
            setHasFixedSize(true)
            layoutManager=mLayoutManager
            adapter=mAdapter
        }
    }
    private fun setUpListeners()=with(bind){
        fabUp.setOnClickListener {
            rvDetail.scrollToPosition(0)

        }
    }
    private fun setUpObservers(){
        loginViewModel.checkIfSessionIsCreated()
        accountViewModel.favoriteList.observe(viewLifecycleOwner,::updateUi)
        accountViewModel.watchlist.observe(viewLifecycleOwner,::updateUi)

        loginViewModel.sessionIdPrefs.observe(viewLifecycleOwner){sessionIdSaved->
            if(sessionIdSaved.isNotEmpty()){
                sessionId=sessionIdSaved
                loginViewModel.fetchUserDetail(sessionIdSaved)
            }
        }
        loginViewModel.userDetail.observe(viewLifecycleOwner){user->
            mUser=user
            when(args.typeOfList){
                FAVORITES_LIST->{
                    (activity as? MainActivity)?.supportActionBar?.title = getString(R.string.favorite_movies_subtitle)
                    accountViewModel.fetchMyFavoriteMovies(user.id.toString(),sessionId.toString(),1)}
                WATCH_LIST->{
                    (activity as? MainActivity)?.supportActionBar?.title = getString(R.string.watchlist_movies_subtitle)
                    accountViewModel.fetchMyWatchlistMovies(user.id.toString(),sessionId.toString(),1)
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
        val bottomNav = (activity as? MainActivity)?.bind?.bottomNav
       bind.rvDetail.addOnScrollListener(object: PaginationRecyclerView(bottomNav,bind.fabUp,mLayoutManager){
            override fun loadMoreItems() {
                isLoading=true
                currentPage+=1
                mAdapter?.addLoadingItem()
                mUser?.let {
                    when(args.typeOfList){
                        FAVORITES_LIST->accountViewModel.fetchMyFavoriteMovies(it.id.toString(),sessionId.toString(),currentPage)
                        WATCH_LIST->accountViewModel.fetchMyWatchlistMovies(it.id.toString(),sessionId.toString(),currentPage)
                    }
                  }
            }
            override fun getTotalPageCount() = totalPages
            override fun isLastPage() = isLastPage
            override fun isLoading() = isLoading
        })
    }

    private fun onMenuItemSelected(){
        val menu:MenuHost = requireActivity()
        menu.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().navigateUp()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.bind?.bottomNav?.visibility=View.VISIBLE
        _bind=null
    }
}