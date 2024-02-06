package com.barryzea.androidflavours.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.androidflavours.common.utils.SingleMutableLiveData
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
class MainViewModel @Inject constructor(private val useCases: UseCases) :ViewModel() {

    var movies:SingleMutableLiveData<DomainMovie> = SingleMutableLiveData()
        private set
    var moviesFound:MutableLiveData<DomainMovie> = MutableLiveData()
        private set
    var infoMsg:MutableLiveData<String> = MutableLiveData()
        private set
    fun fetchMovies(page:Int){
        viewModelScope.launch {
            when(val response = useCases.fetchMovies(page)){
                is TmdbResponse.Success->{
                    movies.value= response.tmdbResult
                }
                is TmdbResponse.Error->{
                    infoMsg.value = response.msg
                }
            }
        }
    }
    fun searchMovie(searchValue:String){
        viewModelScope.launch{
            when(val response = useCases.searchMovie(searchValue)){
                is TmdbResponse.Success->{
                    moviesFound.value= response.tmdbResult
                }
                is TmdbResponse.Error->{
                    infoMsg.value = response.msg
                }
            }
        }
    }
}