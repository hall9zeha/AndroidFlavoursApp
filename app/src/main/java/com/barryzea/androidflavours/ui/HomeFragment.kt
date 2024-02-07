package com.barryzea.androidflavours.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.showSnackbar
import com.barryzea.androidflavours.common.utils.DotsIndicatorDecoration
import com.barryzea.androidflavours.common.utils.PaginationRecyclerView
import com.barryzea.androidflavours.data.entities.Genre
import com.barryzea.androidflavours.data.entities.TmdbMovie
import com.barryzea.androidflavours.databinding.FragmentHomeBinding
import com.barryzea.androidflavours.domain.entities.DomainMovie
import com.barryzea.androidflavours.ui.activities.MainActivity
import com.barryzea.androidflavours.ui.adapters.GenresAdapter
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
    private var genresAdapter:GenresAdapter?= null
    private lateinit var mLayoutManager: GridLayoutManager
    private var currentPage=1
    private var isLoading=false
    private var totalPages=0
    private var isLastPage=false
    private var fetchByGenre=false
    private var genreId=0

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
        setUpObservers(savedInstanceState)

    }
    private fun setUpObservers(savedInstanceState: Bundle?) {
        if(savedInstanceState==null) {
            currentPage = 1
            if(!fetchByGenre) viewModel.fetchMovies(null,currentPage)
            else viewModel.fetchMovies(genreId,currentPage)
            viewModel.fetchGenres()
        }
        viewModel.movies.observe(viewLifecycleOwner, Observer(::updateUi))
        viewModel.infoMsg.observe(viewLifecycleOwner){
            bind.root.showSnackbar(it)
        }
        viewModel.genres.observe(viewLifecycleOwner){response->
            genresAdapter?.let{
                it.add(response.genres)
            }
        }
        viewModel.moviesByGenres.observe(viewLifecycleOwner,Observer(::updateUi))
    }
    private fun updateUi(domainMovie: DomainMovie?) {
        domainMovie?.let {
            setUpShimmerLayout(false)
            if(isLoading && it.movies.isNotEmpty())movieAdapter?.removeLoadingItem()
            totalPages=it.totalPages
            isLastPage=(currentPage >=totalPages)
            isLoading=false
            movieAdapter?.addAll(it.movies)

        }
    }
    private fun setUpAdapter(){

        movieAdapter = MovieAdapter(::onItemClick)
        genresAdapter = GenresAdapter(::onGenreItemClick)
        mLayoutManager=GridLayoutManager(context,2)
        bind.rvMovies.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            adapter=movieAdapter
        }
        bind.rvGenres.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter=genresAdapter
            addItemDecoration(
                DotsIndicatorDecoration(
                    colorActive = ContextCompat.getColor(context,R.color.purple),
                    colorInactive = ContextCompat.getColor(context, R.color.purple_200)
                )
            )
        }

    }

    private fun onGenreItemClick(genre: Genre) {
       movieAdapter?.let{
           fetchByGenre=true
           it.clear()
           currentPage=1
           genreId=genre.id
           viewModel.fetchMovies(genreId,currentPage)
       }
    }

    private fun onItemClick(movie: TmdbMovie) {
        //Al usar la librería safeArgs con navigation se creará una clase con el nombre de nuestro fragment o activity de origen
        //donde tengamos una acción establecida como en este caso que es (HomeFragment), estas clases siempre llevaran adjunto el nombre
        //Directions (HomeFragmentDirections) y nuestra acción recivirá un argumento de tipo "TmdbMovie" (.actionHomeToDetail(movie))
        val action = HomeFragmentDirections.actionHomeToDetail(movie)
        Navigation.findNavController(bind.root).navigate(action)
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
        val bottomNav = (activity as? MainActivity )?.bind?.bottomNav
        bind.rvMovies.addOnScrollListener(object: PaginationRecyclerView(bottomNav,mLayoutManager){
            override fun loadMoreItems() {
                isLoading=true
                currentPage+=1
                movieAdapter?.addLoadingItem()
                if(!fetchByGenre)viewModel.fetchMovies(null,currentPage)
                else viewModel.fetchMovies(genreId,currentPage)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _bind =null
    }
}