package com.barryzea.androidflavours.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.barryzea.androidflavours.common.showSnackbar
import com.barryzea.androidflavours.common.utils.PaginationRecyclerView
import com.barryzea.androidflavours.data.entities.TmdbMovie
import com.barryzea.androidflavours.databinding.FragmentSearchBinding
import com.barryzea.androidflavours.domain.entities.DomainMovie
import com.barryzea.androidflavours.ui.activities.MainActivity
import com.barryzea.androidflavours.ui.adapters.MovieAdapter
import com.barryzea.androidflavours.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _bind: FragmentSearchBinding?=null
    private val viewModel: MainViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var mLayoutManager: GridLayoutManager
    private var currentPage=1
    private var isLoading=false
    private var totalPages=0
    private var isLastPage=false
    private var searchValue:String=""
    private val bind get() = _bind!!
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
            _bind = FragmentSearchBinding.inflate(inflater, container, false)
            _bind?.let {
                return bind.root
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpAdapter()
        setUpPagination()
        setUpObservers(savedInstanceState)
    }
    private fun setUpObservers(savedInstanceState: Bundle?) {
        if(savedInstanceState !=null){
            currentPage=1
            movieAdapter.clear()

        }
        viewModel.searchValue.observe(viewLifecycleOwner){
            viewModel.searchMovie(it,page=null)
        }
        viewModel.moviesFound.observe(viewLifecycleOwner){
           it?.let{result->
                if(result.movies.isNotEmpty()){
                    setUpShimmerLayout(false)
                    updateUi(result)
                }else{
                    setUpShimmerLayout(false)
                    //Si usamos cualquier vista dentro del coordinator layuot el snackbar no se lanzará
                    //por eso traemos la siguiente vista
                    val view = activity?.window?.decorView?.findViewById<View>(android.R.id.content)
                    view?.showSnackbar("No hay  resultados para ${bind.edtSearch.text}")
                    bind.amountFoundChip.apply {
                        visibility=View.GONE
                        text=""
                    }
                }
            }
        }
        viewModel.infoMsg.observe(viewLifecycleOwner){
            bind.root.showSnackbar(it)
        }

    }
    private fun setUpAdapter(){
        movieAdapter = MovieAdapter(::onItemClick)
        mLayoutManager= GridLayoutManager(context, 3)
        bind.rvSearch.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            adapter=movieAdapter
        }
    }
    private fun setUpListeners()=with(bind){
        tilSearch.setEndIconOnClickListener {
            if(edtSearch.text.toString().isNotEmpty()){
                movieAdapter.clear()
                viewModel.setSearchValue(edtSearch.text.toString())
                setUpShimmerLayout(true)
            }
            edtSearch.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }
        edtSearch.setOnEditorActionListener{_,actionId,_->
            if(actionId== EditorInfo.IME_ACTION_SEARCH){
                if(edtSearch.text.toString().isNotEmpty()){
                    movieAdapter.clear()
                    viewModel.setSearchValue(edtSearch.text.toString())
                    setUpShimmerLayout(true)
                }
                edtSearch.onEditorAction(EditorInfo.IME_ACTION_DONE)
            }
            false
        }
    }
    private fun setUpShimmerLayout(enable:Boolean){
        if(enable){
            bind.shimmerLoading.shimmerLoading.visibility= View.VISIBLE
            bind.shimmerLoading.shimmerLoading.startShimmer()
        }else{
            bind.shimmerLoading.shimmerLoading.stopShimmer()
            bind.shimmerLoading.shimmerLoading.visibility = View.GONE
        }
    }
    private fun onItemClick(movie: TmdbMovie) {
        val action = SearchFragmentDirections.actionSearchToDetail(movie)
        Navigation.findNavController(bind.root).navigate(action)
    }
    private fun updateUi(domainMovie: DomainMovie?) {
        domainMovie?.let {
            bind.amountFoundChip.apply {
                visibility=View.VISIBLE
                text=domainMovie.totalResult.toString()
            }
            setUpShimmerLayout(false)
            if(isLoading && it.movies.isNotEmpty())movieAdapter?.removeLoadingItem()
            totalPages=it.totalPages
            isLastPage=(currentPage >=totalPages)
            isLoading=false
            movieAdapter.addAll(it.movies)

        }
    }
    private fun setUpPagination(){
        //Obtenemos la referencia a nuestro bottomNavigationView que está en MainActivity para enviarla a nuestro paginador
        //del recyclerView y pueda mostrarlo u ocultarlo mientras nos desplazamos.
        val bottomNav = (activity as? MainActivity)?.bind?.bottomNav
        bind.rvSearch.addOnScrollListener(object: PaginationRecyclerView(bottomNav,mLayoutManager){
            override fun loadMoreItems() {
                isLoading=true
                currentPage+=1
                movieAdapter?.addLoadingItem()
                viewModel.searchMovie(bind.edtSearch.text.toString(),currentPage)

            }
            override fun getTotalPageCount() = totalPages
            override fun isLastPage() = isLastPage
            override fun isLoading() = isLoading
        })
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}