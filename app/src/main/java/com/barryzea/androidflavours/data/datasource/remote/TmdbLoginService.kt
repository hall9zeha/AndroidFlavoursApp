package com.barryzea.androidflavours.data.datasource.remote

import com.barryzea.androidflavours.data.entities.RequestToken
import com.barryzea.androidflavours.data.entities.Session
import com.barryzea.androidflavours.domain.entities.CreateSessionRequest
import com.barryzea.androidflavours.domain.entities.ValidateLoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/

interface TmdbLoginService {
    @POST("authentication/token/new")
    suspend fun getRequestToken(
        @Query("api_key") apiKey:String
    ): Response<RequestToken>

    @POST("authentication/token/validate_with_login")
    suspend fun validateWithLogin(
        @Query("api_key")apiKey:String,
        @Body request:ValidateLoginRequest
    ):Response<RequestToken>

    @POST("authentication/session/new")
    suspend fun createSession(
        @Query("api_key")apiKey: String,
        @Body request:CreateSessionRequest
    ):Response<Session>
}