package com.barryzea.androidflavours.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.androidflavours.common.utils.SingleMutableLiveData
import com.barryzea.androidflavours.data.entities.TmdbMovie
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.domain.entities.DomainMovie
import com.barryzea.androidflavours.domain.entities.toDomain
import com.barryzea.androidflavours.domain.usecase.AccountUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 20/02/2024.
 **/
@HiltViewModel
class AccountViewModel @Inject constructor(private val useCases: AccountUseCases): ViewModel() {
    private var _favoriteList:MutableLiveData<DomainMovie> = MutableLiveData()
    val favoriteList:LiveData<DomainMovie> = _favoriteList

    private var _watchlist:MutableLiveData<DomainMovie> = MutableLiveData()
    val watchlist:LiveData<DomainMovie> = _watchlist

    private var _msgInfo:SingleMutableLiveData<String> = SingleMutableLiveData()
    val msgInfo:LiveData<String> = _msgInfo

    fun fetchMyFavoriteMovies(accountId:String,sessionId:String, page:Int){
        viewModelScope.launch {
            when(val response = useCases.fetchMyFavoriteMovies(accountId,sessionId, page)){
                is TmdbResponse.Success->_favoriteList.value = response.tmdbResult.toDomain()
                is TmdbResponse.Error->_msgInfo.value = response.msg
            }
        }
    }
    fun fetchMyWatchlistMovies(accountId:String,sessionId:String, page:Int){
        viewModelScope.launch {
            when(val response = useCases.fetchMyWatchlistMovies(accountId,sessionId, page)){
                is TmdbResponse.Success->_watchlist.value = response.tmdbResult.toDomain()
                is TmdbResponse.Error->_msgInfo.value = response.msg
            }
        }
    }
}