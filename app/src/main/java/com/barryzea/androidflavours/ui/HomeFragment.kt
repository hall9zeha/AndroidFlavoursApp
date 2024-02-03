package com.barryzea.androidflavours.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.utils.PaginationRecyclerView
import com.barryzea.androidflavours.data.entities.TmdbMovie
import com.barryzea.androidflavours.data.entities.TmdbResult
import com.barryzea.androidflavours.databinding.FragmentHomeBinding
import com.barryzea.androidflavours.ui.adapters.MovieAdapter
import com.barryzea.androidflavours.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _bind:FragmentHomeBinding?=null
    private val bind get() = _bind!!

    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: MainViewModel by viewModels()
    private  var movieAdapter: MovieAdapter?=null
    private lateinit var mLayoutManager: GridLayoutManager
    private var currentPage=1
    private var isLoading=false
    private var totalPages=0
    private var isLastPage=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let{
            _bind = FragmentHomeBinding.inflate(inflater,container,false)
            _bind?.let{
                return it.root
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpShimmerLayout(true)
        setUpAdapter()
        setUpPagination()
        viewModel.fetchMovies(1)
        viewModel.movies.observe(viewLifecycleOwner, Observer(::updateUi))
        viewModel.infoMsg.observe(viewLifecycleOwner){
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

        movieAdapter = MovieAdapter(::onItemClick)
        mLayoutManager=GridLayoutManager(context,3)
        bind.rvMovies.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            adapter=movieAdapter
        }
    }

    private fun onItemClick(movie: TmdbMovie) {
        Navigation.findNavController(bind.root).navigate(R.id.action_home_to_detail)
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
        bind.rvMovies.addOnScrollListener(object: PaginationRecyclerView(mLayoutManager){
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
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}