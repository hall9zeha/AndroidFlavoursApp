package com.barryzea.androidflavours.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.barryzea.androidflavours.common.FAVORITES_LIST
import com.barryzea.androidflavours.common.WATCH_LIST
import com.barryzea.androidflavours.common.showSnackbar
import com.barryzea.androidflavours.databinding.ActivityAccountMovieListBinding
import com.barryzea.androidflavours.databinding.FragmentUserAccountBinding
import com.barryzea.androidflavours.ui.adapters.AccountMoviesAdapter
import com.barryzea.androidflavours.ui.viewmodel.AccountViewModel
import com.barryzea.androidflavours.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class UserAccountFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private  var _bind:FragmentUserAccountBinding?=null
    private val bind:FragmentUserAccountBinding get() = _bind!!
    private val loginViewModel: LoginViewModel by viewModels()
    private val accountViewModel:AccountViewModel by viewModels()
    private var isLogin:Boolean=false
    private var sessionId:String?=null
    private var mAdapter: AccountMoviesAdapter?=null


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
            _bind = FragmentUserAccountBinding.inflate(inflater,container,false)
            _bind?.let{bind->
                return bind.root
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpListeners()
        setUpObservers()
       }
    private fun setUpListeners()=with(bind){
        tvFavoriteMovies.setOnClickListener{
            val actionShowMyFavorites = UserAccountFragmentDirections.goToAccountMoviesList(FAVORITES_LIST)
            Navigation.findNavController(bind.root).navigate(actionShowMyFavorites)
        }
        tvWatchListMovies.setOnClickListener {
            val actionShowMyWatchList = UserAccountFragmentDirections.goToAccountMoviesList(WATCH_LIST)
            Navigation.findNavController(bind.root).navigate(actionShowMyWatchList)
        }
    }
    private fun setUpAdapter()=with(bind){
        mAdapter = AccountMoviesAdapter{
            val action = UserAccountFragmentDirections.actionHomeToDetail(it)
            Navigation.findNavController(bind.root).navigate(action)
        }
        rvFavorites.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter=mAdapter
        }
    }

    private fun setUpObservers(){
        loginViewModel.checkIfSessionIsCreated()
        loginViewModel.sessionIdPrefs.observe(viewLifecycleOwner){sessionIdSaved->
            if(sessionIdSaved.isNotEmpty()){
                isLogin = true
                sessionId=sessionIdSaved
                loginViewModel.fetchUserDetail(sessionIdSaved)
            }
        }
        loginViewModel.userDetail.observe(viewLifecycleOwner){user->
            accountViewModel.fetchMyFavoriteMovies(user.id.toString(),sessionId.toString(),1)
        }
        accountViewModel.favoriteList.observe(viewLifecycleOwner){favorites->
            favorites?.let{
                mAdapter?.addAll(it.movies)
            }
        }
        accountViewModel.msgInfo.observe(viewLifecycleOwner){
            bind.root.showSnackbar(it)
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _bind=null
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserAccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}