package com.barryzea.androidflavours.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.androidflavours.common.utils.DataStorePreferences
import com.barryzea.androidflavours.common.utils.SingleMutableLiveData
import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.data.entities.PrefsEntity
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.domain.entities.DomainMovie
import com.barryzea.androidflavours.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/

@HiltViewModel
class MainViewModel @Inject constructor(private val useCases: UseCases, private val prefs:DataStorePreferences) :ViewModel() {

    private var _movies:SingleMutableLiveData<DomainMovie> = SingleMutableLiveData()
    val  movies:SingleMutableLiveData<DomainMovie> = _movies

    private var _moviesFound:SingleMutableLiveData<DomainMovie> = SingleMutableLiveData()
    val moviesFound:SingleMutableLiveData<DomainMovie> = _moviesFound

    private var _infoMsg:MutableLiveData<String> = MutableLiveData()
    val infoMsg:MutableLiveData<String> = _infoMsg

    private var _genres:MutableLiveData<Genres> = MutableLiveData()
    val genres:MutableLiveData<Genres> = _genres

    private var _searchValue:MutableLiveData<String> = MutableLiveData()
    val searchValue:MutableLiveData<String> = _searchValue

    private var _preferences:MutableLiveData<PrefsEntity> = MutableLiveData()
    val preferences:LiveData<PrefsEntity> = _preferences
    fun getPreferences(){
        viewModelScope.launch {
            prefs.getFromDatastore().collect{
                _preferences.value=it
            }
        }
    }
    fun clearPreferences(){ viewModelScope.launch {prefs.clearDatastore() } }
    fun fetchMovies(genreId:Int?,page:Int){
        viewModelScope.launch {
            when(val response = useCases.fetchMovies(genreId,page)){
                is TmdbResponse.Success ->{
                    _movies.value= response.tmdbResult
                }
                is TmdbResponse.Error->{
                    _infoMsg.value = response.msg
                }
            }
        }
    }
    fun fetchMoviesSortedBy(sortedValue:String, page: Int){
        viewModelScope.launch {
            when(val response = useCases.fetchMoviesSortedBy(sortedValue,page)){
                is TmdbResponse.Success ->{
                    _movies.value=response.tmdbResult
                }
                is TmdbResponse.Error->{
                    _infoMsg.value = response.msg
                }
            }
        }
    }
    fun searchMovie(searchValue:String,page: Int?){
        viewModelScope.launch{
            when(val response = useCases.searchMovie(searchValue,page)){
                is TmdbResponse.Success->{
                    _moviesFound.value= response.tmdbResult
                }
                is TmdbResponse.Error->{
                    _infoMsg.value = response.msg
                }
            }
        }
    }
    fun fetchGenres(){
        viewModelScope.launch {
            when(val response = useCases.fetchGenres()){
                is TmdbResponse.Success->{
                    _genres.value = response.tmdbResult
                }
                is TmdbResponse.Error->{
                    _infoMsg.value = response.msg

                }
            }
        }
    }
    fun setSearchValue(stringValue:String){
        viewModelScope.launch {
            _searchValue.value=stringValue
        }
    }

}