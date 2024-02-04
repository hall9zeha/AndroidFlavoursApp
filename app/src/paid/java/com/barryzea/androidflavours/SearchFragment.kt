package com.barryzea.androidflavours

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import com.barryzea.androidflavours.databinding.FragmentSearchBinding
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
        setUpObservers()
    }
    private fun setUpObservers(){
        viewModel.moviesFound.observe(viewLifecycleOwner){
            Log.e("SEARCH", it.movies.toString() )
        }
        viewModel.infoMsg.observe(viewLifecycleOwner){
            Log.e("SEARCH-ERROR", it )
        }
    }
    private fun setUpListeners()=with(bind){
        tilSearch.setEndIconOnClickListener {
            if(edtSearch.text.toString().isNotEmpty()) viewModel.searchMovie(edtSearch.text.toString())
            edtSearch.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }
        edtSearch.setOnEditorActionListener{_,actionId,_->
            if(actionId==EditorInfo.IME_ACTION_SEARCH){
                if(edtSearch.text.toString().isNotEmpty()) viewModel.searchMovie(edtSearch.text.toString())
            }

            false
        }
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
}