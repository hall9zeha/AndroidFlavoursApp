package com.barryzea.androidflavours.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.BY_CATEGORY
import com.barryzea.androidflavours.common.BY_GENRE
import com.barryzea.androidflavours.common.NONE
import com.barryzea.androidflavours.common.POPULAR
import com.barryzea.androidflavours.common.TOP_RATED
import com.barryzea.androidflavours.common.UPCOMING
import com.barryzea.androidflavours.common.showSnackbar
import com.barryzea.androidflavours.common.utils.DotsIndicatorDecoration
import com.barryzea.androidflavours.common.utils.PaginationRecyclerView
import com.barryzea.androidflavours.data.entities.Genre
import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.data.entities.TmdbMovie
import com.barryzea.androidflavours.databinding.FragmentHomeBinding
import com.barryzea.androidflavours.domain.entities.DomainMovie
import com.barryzea.androidflavours.ui.activities.MainActivity
import com.barryzea.androidflavours.ui.adapters.GenresAdapter
import com.barryzea.androidflavours.ui.adapters.MovieAdapter
import com.barryzea.androidflavours.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    private var _bind: FragmentHomeBinding?=null
    private val bind get() = _bind!!

    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: MainViewModel by viewModels()

    private  var movieAdapter: MovieAdapter?=null
    private var genresAdapter: GenresAdapter?= null
    private lateinit var mLayoutManager: GridLayoutManager
    private var currentPage=1
    private var isLoading=false
    private var totalPages=0
    private var isLastPage=false
    private var fetchBy=0
    private var genreId=0
    private var CATEGORY=""


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
            _bind = FragmentHomeBinding.inflate(inflater, container, false)
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
        setUpListeners()
        setUpPagination()
        setUpObservers(savedInstanceState)

    }

    private fun setUpListeners() {
        (activity as? MainActivity)?.bind?.ctlHeader?.visibility = View.VISIBLE
        bind.chipPopular.setOnClickListener { fetchMoviesByCategory(POPULAR) }
        bind.chipTopRated.setOnClickListener {fetchMoviesByCategory(TOP_RATED) }
        bind.chipDiscover.setOnClickListener {
            fetchBy= NONE
            movieAdapter?.clear()
            viewModel.fetchMovies(null,1) }
        bind.chipUpcoming.setOnClickListener {fetchMoviesByCategory(UPCOMING) }
    }
    private fun fetchMoviesByCategory(category:String){
        fetchBy = BY_CATEGORY
        CATEGORY = category
        movieAdapter?.clear()
        viewModel.fetchMoviesSortedBy(category,1)
    }
    private fun setUpObservers(savedInstanceState: Bundle?) {
        viewModel.getPreferences()
        viewModel.preferences.observe(viewLifecycleOwner){preferences->
            if(preferences.sessionId!!.isNotEmpty()){
                (activity as? MainActivity)?.bind?.ctlHeader?.visibility = View.VISIBLE
            }
        }
        if(savedInstanceState==null) {
            currentPage = 1
            when(fetchBy){
                BY_GENRE ->{viewModel.fetchMovies(genreId,currentPage)}
                BY_CATEGORY ->{viewModel.fetchMoviesSortedBy(CATEGORY,currentPage)}
                else->{viewModel.fetchMovies(null,currentPage)}
            }

        }else{
            viewModel.fetchMovies(null,currentPage)
        }

        viewModel.fetchGenres()
        viewModel.movies.observe(viewLifecycleOwner, Observer(::updateUi))
        viewModel.infoMsg.observe(viewLifecycleOwner){
            bind.root.showSnackbar(it)
            Log.e("ERROR_MSG", it )
        }
        viewModel.genres.observe(viewLifecycleOwner){response->
            response.genres?.let{
                    genresAdapter?.add(it)
                }
            }
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
        mLayoutManager= GridLayoutManager(context, 3)
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
                    colorActive = ContextCompat.getColor(context, R.color.yellow),
                    colorInactive = ContextCompat.getColor(
                        context,
                        R.color.md_theme_primaryContainer
                    )
                )
            )
        }
    }
    private fun onGenreItemClick(genre: Genre) {
       movieAdapter?.let{
           fetchBy= BY_GENRE
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
        //Obtenemos la referencia a nuestro bottomNavigationView que está en MainActivity para enviarla a nuestro paginador
        //del recyclerView y pueda mostrarlo u ocultarlo mientras nos desplazamos.
        val bottomNav = (activity as? MainActivity)?.bind?.bottomNav
        bind.rvMovies.addOnScrollListener(object: PaginationRecyclerView(bottomNav,mLayoutManager){
            override fun loadMoreItems() {
                isLoading=true
                currentPage+=1
                movieAdapter?.addLoadingItem()
                when(fetchBy){
                    BY_GENRE ->{viewModel.fetchMovies(genreId,currentPage)}
                    BY_CATEGORY ->{viewModel.fetchMoviesSortedBy(CATEGORY,currentPage)}
                    else->{viewModel.fetchMovies(null,currentPage)}
                }
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