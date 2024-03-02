package com.barryzea.androidflavours.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.androidflavours.common.utils.DataStorePreferences
import com.barryzea.androidflavours.common.utils.SingleMutableLiveData
import com.barryzea.androidflavours.data.entities.Cast
import com.barryzea.androidflavours.data.entities.CharacterMovie
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.entities.TrailerMovie
import com.barryzea.androidflavours.data.entities.Trailers
import com.barryzea.androidflavours.domain.entities.DomainAuth
import com.barryzea.androidflavours.domain.entities.toDomain
import com.barryzea.androidflavours.domain.usecase.AccountUseCases
import com.barryzea.androidflavours.domain.usecase.LoginUseCases
import com.barryzea.androidflavours.domain.usecase.MovieUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 13/02/2024.
 **/

@HiltViewModel
class DetailViewModel @Inject constructor(private val preferences: DataStorePreferences,
                                          private val loginUseCases: LoginUseCases,
                                          private val useCases: MovieUseCases,
                                          private val accountUseCase:AccountUseCases) :ViewModel() {
    private var mSessionId:String?=null

    private var _credits:SingleMutableLiveData<List<CharacterMovie>> = SingleMutableLiveData()
    val credits:LiveData<List<CharacterMovie>> = _credits
    private var _infoMsg:MutableLiveData<String> = MutableLiveData()
    val infoMsg:LiveData<String> = _infoMsg

    //Estas propiedades no se usan en ninguna vista aún
    private var _trailers:MutableLiveData<List<TrailerMovie>> = MutableLiveData()
    val trailers:LiveData<List<TrailerMovie>> = _trailers

    private var _userDetail:MutableLiveData<DomainAuth> = MutableLiveData()
    val userDetail:LiveData<DomainAuth> = _userDetail

    private var _favoriteAdded:MutableLiveData<DomainAuth> = MutableLiveData()
    val favoriteAdded:LiveData<DomainAuth> = _favoriteAdded
    private var _watchlistAdded:MutableLiveData<DomainAuth> = MutableLiveData()
    val watchlistAdded:LiveData<DomainAuth> = _watchlistAdded

    private var _isLogin:MutableLiveData<Boolean> = MutableLiveData()
    val isLogin:LiveData<Boolean> = _isLogin
    init{
        viewModelScope.launch {
            preferences.getFromDatastore().collect{pref->
                if(pref.sessionId.toString().isNotEmpty()){
                    _isLogin.value=true
                    mSessionId=pref.sessionId
                    fetUserDetail(pref.sessionId!!)
                }
            }
        }
    }
    private fun fetUserDetail(sessionId:String){
        viewModelScope.launch {
            when(val response = loginUseCases.fetchUserDetails(sessionId)){
                is TmdbResponse.Success->_userDetail.value=response.tmdbResult.toDomain()
                is TmdbResponse.Error->_infoMsg.value=response.msg
            }
        }
    }

    //Podemos usar esta forma si queremos realizar peticiones  secuencialmente a nuestra API
   /* fun fetchMovieCredits(idMovie:Int){
        viewModelScope.launch {
            when(val response=useCases.fetchCredits(idMovie)){
                is TmdbResponse.Success->{_credits.value=response.tmdbResult}
                is TmdbResponse.Error->_infoMsg.value = response.msg
            }
        }
    }*/
    //Podemos usar esta forma para realizar multiples llamadas a nuestra API al mismo tiempo
    fun fetchCreditsAndTrailer(idMovie: Int){
        viewModelScope.launch {

            //Realizamos dos llamadas a la API simultaneamente
            val creditsDef = async { useCases.fetchCredits(idMovie) }
            val trailersDef = async { useCases.fetchTrailers(idMovie) }

            val (credits, trailers) = awaitAll(creditsDef, trailersDef)

            when {
                (credits is TmdbResponse.Success && trailers is TmdbResponse.Success) ->{
                    //A causa de que nuestra clase TmdbResponse es de tipo genérico debemos castear al tipo  de dato esperado, según corresponda
                    //_credits.value = (credits.tmdbResult as List<CharacterMovie>)
                    _credits.value = (credits.tmdbResult as Cast).cast
                    _trailers.value = (trailers.tmdbResult as Trailers).trailers
                }
               credits is TmdbResponse.Error -> {
                    _infoMsg.value = credits.msg
                }

                trailers is TmdbResponse.Error -> {
                    _infoMsg.value=trailers.msg
                }
            }

        }}
    fun addToFavorite(idMovie:Int){
        viewModelScope.launch {
            mSessionId?.let{sessionId->
                val response = accountUseCase.addToFavorite(_userDetail.value?.id!!, sessionId,idMovie)
                when(response){
                    is TmdbResponse.Success->_favoriteAdded.value=response.tmdbResult.toDomain()
                    is TmdbResponse.Error->_infoMsg.value=response.msg
                }
            }
        }
    }
    fun addToWatchlist(idMovie:Int){
        viewModelScope.launch {
            mSessionId?.let{sessionId->
                val response = accountUseCase.addToWatchlist(_userDetail.value?.id!!, sessionId,idMovie)
                when(response){
                    is TmdbResponse.Success->_watchlistAdded.value=response.tmdbResult.toDomain()
                    is TmdbResponse.Error->_infoMsg.value=response.msg
                }
            }
        }
    }
}