package com.barryzea.androidflavours.data.repository

import com.barryzea.androidflavours.data.datasource.remote.RetrofitService
import com.barryzea.androidflavours.data.entities.Logout
import com.barryzea.androidflavours.data.entities.RequestToken
import com.barryzea.androidflavours.data.entities.User
import com.barryzea.androidflavours.domain.entities.CreateSessionRequest
import com.barryzea.androidflavours.domain.entities.ValidateLoginRequest
import retrofit2.Response
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/

class LoginRepositoryImpl @Inject constructor(retrofit:RetrofitService, private val apiKey:String):LoginRepository {
    private val loginService = retrofit.loginService()
    override suspend fun getRequestToken(): Response<RequestToken> = loginService.getRequestToken(apiKey)
    override suspend fun validateWithLogin(request: ValidateLoginRequest) = loginService.validateWithLogin(apiKey,request)
    override suspend fun createSession(request: CreateSessionRequest) = loginService.createSession(apiKey,request)
    override suspend fun fetchUserDetails(sessionId: String) = loginService.fetchUserDetail(apiKey,sessionId)
    override suspend fun logout(sessionId: String) = loginService.logout(apiKey,sessionId)
}