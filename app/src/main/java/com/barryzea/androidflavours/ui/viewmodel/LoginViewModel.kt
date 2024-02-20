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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    private var _createdSessionId:SingleMutableLiveData<String> = SingleMutableLiveData()
    val createdSessionId:LiveData<String> = _createdSessionId

    private var _sessionIdPrefs:MutableLiveData<String> = MutableLiveData()
    val sessionIdPrefs:LiveData<String> = _sessionIdPrefs

    private var _userDetail:MutableLiveData<DomainAuth> = MutableLiveData()
    val userDetail:LiveData<DomainAuth> = _userDetail

    private var _logoutSuccess:SingleMutableLiveData<Boolean> = SingleMutableLiveData()
    val logoutSuccess:LiveData<Boolean> = _logoutSuccess

    // Usamos un canal para recibir y compartir los tokens (entre las corrutinas) devueltos
    // en nuestras peticiones a la API
    private val sessionTokenChannel:Channel<String> = Channel()

    // 1- Realizamos una nueva petición de token
    fun requestNewToken(){
        viewModelScope.launch {
            when (val response = useCases.getRequestToken()) {
                is TmdbResponse.Success-> if(response.tmdbResult.success){
                    //Si se recibió el token lo enviamos a través de nuestro canal a las corrutinas que lo observan
                   sessionTokenChannel.send(response.tmdbResult.token!!)}
                is TmdbResponse.Error-> _msgInfo.value = response.msg
            }
        }
    }

    // 2- Una vez recibido el token lo enviamos junto a nuestros datos de usuario a validar a la API
    fun validateWithLogin(requestLogin:ValidateLoginRequest){
        viewModelScope.launch {
            //Ejecutamos la función del paso 1
            requestNewToken()
            //Aquí es donde recibimos el token del paso 1
            val tokenCreated= sessionTokenChannel.receive()

            //Agregamos el token a los datos del usuario para enviarla ala API y crear la sesión
            val requestUser=ValidateLoginRequest(
                username = requestLogin.username,
                password = requestLogin.password,
                requestToken = tokenCreated
            )
            when(val response = useCases.validateWithLogin(requestUser)){
                is TmdbResponse.Success->{
                    if(response.tmdbResult.success){
                       createSession(CreateSessionRequest(response.tmdbResult.token))
                    }
                    else _msgInfo.value="Usuario o password incorrectos"
                }
                is TmdbResponse.Error-> _msgInfo.value = response.msg
            }
        }
    }
    // 3- Mandamos una petición para crear la sesión
    private fun createSession(requestToken:CreateSessionRequest){
        viewModelScope.launch {
            //Una vez creada la sesión de obtenido el token de sessión, manejamos dicho token como mejor nos convenga
            //En este caso se guardará en preferencias
            when(val response = useCases.createSession(requestToken)){
                is TmdbResponse.Success-> if(response.tmdbResult.success) _createdSessionId.value=response.tmdbResult.token!!
                is TmdbResponse.Error-> _msgInfo.value = response.msg
            }
        }
    }

    fun saveSessionId(sessionId:String){
        viewModelScope.launch {
            datastore.saveToDatastore(PrefsEntity(sessionId=sessionId))
        }
    }
    fun checkIfSessionIsCreated(){
        viewModelScope.launch {
            datastore.getFromDatastore().collect{
                _sessionIdPrefs.value = it.sessionId!!
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
    fun logout(sessionId: String){
        viewModelScope.launch {
            when(val response=useCases.logout(sessionId)){
                is TmdbResponse.Success-> _logoutSuccess.value = response.tmdbResult.success
                is TmdbResponse.Error->_msgInfo.value=response.msg
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        sessionTokenChannel.close()
    }
}