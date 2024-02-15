package com.barryzea.androidflavours.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.androidflavours.common.utils.SingleMutableLiveData
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.domain.entities.CreateSessionRequest
import com.barryzea.androidflavours.domain.entities.DomainAuth
import com.barryzea.androidflavours.domain.entities.ValidateLoginRequest
import com.barryzea.androidflavours.domain.usecase.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCases: LoginUseCases):ViewModel() {

    private var _msgInfo:MutableLiveData<String> = MutableLiveData()
    val msgInfo:LiveData<String> = _msgInfo

    private var _newToken:MutableLiveData<String> = MutableLiveData()
    val newToken:LiveData<String> = _newToken

    private var _sessionId:MutableLiveData<String> = MutableLiveData()
    val sessionId:LiveData<String> = _sessionId

    private var _authResponse:MutableLiveData<DomainAuth> = MutableLiveData()
    val authResponse:LiveData<DomainAuth> = _authResponse


    fun requestNewToken(){
        viewModelScope.launch {
            when (val response = useCases.getRequestToken()) {
                is TmdbResponse.Success-> if(response.tmdbResult.success) _newToken.value=response.tmdbResult.token!!
                is TmdbResponse.Error-> _msgInfo.value = response.msg
            }
        }
    }
    fun validateWithLogin(requestLogin:ValidateLoginRequest){
        viewModelScope.launch {
            when(val response = useCases.validateWithLogin(requestLogin)){
                is TmdbResponse.Success-> if(response.tmdbResult.success) _authResponse.value=response.tmdbResult!!
                is TmdbResponse.Error-> _msgInfo.value = response.msg
            }

        }
    }
    fun createSession(requestToken:CreateSessionRequest){
        viewModelScope.launch {
            when(val response = useCases.createSession(requestToken)){
                is TmdbResponse.Success-> if(response.tmdbResult.success) _sessionId.value=response.tmdbResult.token!!
                is TmdbResponse.Error-> _msgInfo.value = response.msg
            }
        }
    }



}