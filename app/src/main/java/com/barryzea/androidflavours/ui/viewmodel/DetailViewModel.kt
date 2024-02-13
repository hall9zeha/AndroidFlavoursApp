package com.barryzea.androidflavours.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.androidflavours.data.entities.CharacterMovie
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 13/02/2024.
 **/

@HiltViewModel
class DetailViewModel @Inject constructor(private val useCases: UseCases) :ViewModel() {

    private var _credits:MutableLiveData<List<CharacterMovie>> = MutableLiveData()
    val credits:LiveData<List<CharacterMovie>> = _credits
    private var _infoMsg:MutableLiveData<String> = MutableLiveData()
    val infoMsg:LiveData<String> = _infoMsg

    fun fetchMovieCredits(idMovie:Int){
        viewModelScope.launch {
            when(val response=useCases.fetchCredits(idMovie)){
                is TmdbResponse.Success->{_credits.value=response.tmdbResult}
                is TmdbResponse.Error->_infoMsg.value = response.msg
            }
        }
    }
}