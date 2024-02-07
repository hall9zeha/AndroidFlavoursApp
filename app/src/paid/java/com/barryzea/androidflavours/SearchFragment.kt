package com.barryzea.androidflavours

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.barryzea.androidflavours.common.showSnackbar
import com.barryzea.androidflavours.data.entities.TmdbMovie
import com.barryzea.androidflavours.databinding.FragmentSearchBinding
import com.barryzea.androidflavours.ui.HomeFragmentDirections
import com.barryzea.androidflavours.ui.adapters.MovieAdapter
import com.barryzea.androidflavours.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _bind:FragmentSearchBinding?=null
    private val viewModel:MainViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var mLayoutManager: GridLayoutManager
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
            _bind = FragmentSearchBinding.inflate(inflater, container,false)
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
        setUpObservers()
    }
    private fun setUpObservers(){
        viewModel.moviesFound.observe(viewLifecycleOwner){
           it?.let{result->
                if(result.movies.isNotEmpty()){
                    setUpShimmerLayout(false)
                    movieAdapter.addAll(result.movies)
                }else{
                    setUpShimmerLayout(false)
                    bind.root.showSnackbar("No hay  resultados para ${bind.edtSearch.text}")
                }
            }
        }
        viewModel.infoMsg.observe(viewLifecycleOwner){
            bind.root.showSnackbar(it)
        }
    }
    private fun setUpAdapter(){
        movieAdapter = MovieAdapter(::onItemClick)
        mLayoutManager= GridLayoutManager(context,3)
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
                viewModel.searchMovie(edtSearch.text.toString())}
            edtSearch.onEditorAction(EditorInfo.IME_ACTION_DONE)
            setUpShimmerLayout(true)
        }
        edtSearch.setOnEditorActionListener{_,actionId,_->
            if(actionId==EditorInfo.IME_ACTION_SEARCH){
                setUpShimmerLayout(true)
                if(edtSearch.text.toString().isNotEmpty()){
                    movieAdapter.clear()
                    viewModel.searchMovie(edtSearch.text.toString())}
                edtSearch.onEditorAction(EditorInfo.IME_ACTION_DONE)
            }

            false
        }
    }
    private fun setUpShimmerLayout(enable:Boolean){
        if(enable){
            bind.shimmerLoading.shimmerLoading.visibility=View.VISIBLE
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