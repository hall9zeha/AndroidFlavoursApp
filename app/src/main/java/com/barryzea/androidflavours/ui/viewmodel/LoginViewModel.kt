package com.barryzea.androidflavours.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.androidflavours.common.utils.DataStorePreferences
import com.barryzea.androidflavours.common.utils.SingleMutableLiveData
import com.barryzea.androidflavours.data.entities.PrefsEntity
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.domain.entities.CreateSessionRequest
import com.barryzea.androidflavours.domain.entities.DomainAuth
import com.barryzea.androidflavours.domain.entities.ValidateLoginRequest
import com.barryzea.androidflavours.domain.usecase.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCases: LoginUseCases, val datastore:DataStorePreferences):ViewModel() {

    private var _msgInfo:MutableLiveData<String> = MutableLiveData()
    val msgInfo:LiveData<String> = _msgInfo

    private var _newToken:SingleMutableLiveData<String> = SingleMutableLiveData()
    val newToken:LiveData<String> = _newToken

    private var _authResponse:SingleMutableLiveData<DomainAuth> = SingleMutableLiveData()
    val authResponse:LiveData<DomainAuth> = _authResponse

    private var _sessionId:SingleMutableLiveData<String> = SingleMutableLiveData()
    val sessionId:LiveData<String> = _sessionId

    private var _sessionCreatedId:MutableLiveData<String> = MutableLiveData()
    val sessionCreatedId:LiveData<String> = _sessionCreatedId

    private var _userDetail:MutableLiveData<DomainAuth> = MutableLiveData()
    val userDetail:LiveData<DomainAuth> = _userDetail

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
                is TmdbResponse.Success->{
                    if(response.tmdbResult.success) _authResponse.value=response.tmdbResult!!
                    else _msgInfo.value="Usuario o password incorrectos"
                }
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
    fun saveSessionId(sessionId:String){
        viewModelScope.launch {
            datastore.saveToDatastore(PrefsEntity(sessionId=sessionId))
        }
    }
    fun checkIsSessionIsCreated(){
        viewModelScope.launch {
            datastore.getFromDatastore().collect{
                _sessionCreatedId.value = it.sessionId!!
            }
        }
    }
    fun fetchUserDetail(sessionId: String){
        viewModelScope.launch {
            when(val response = useCases.fetchUserDetails(sessionId)){
                is TmdbResponse.Success->_userDetail.value=response.tmdbResult
                is TmdbResponse.Error->_msgInfo.value=response.msg
            }
        }
    }

}