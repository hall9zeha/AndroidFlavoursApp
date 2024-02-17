package com.barryzea.androidflavours.data.repository

import com.barryzea.androidflavours.data.entities.RequestToken
import com.barryzea.androidflavours.data.entities.Session
import com.barryzea.androidflavours.data.entities.User
import com.barryzea.androidflavours.domain.entities.CreateSessionRequest
import com.barryzea.androidflavours.domain.entities.ValidateLoginRequest
import retrofit2.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 * Copyright (c)  All rights reserved.
 **/
interface LoginRepository {
    suspend fun getRequestToken():Response<RequestToken>
    suspend fun validateWithLogin(request:ValidateLoginRequest):Response<RequestToken>
    suspend fun createSession(request:CreateSessionRequest):Response<Session>
    suspend fun fetchUserDetails(sessionId:String):Response<User>
}