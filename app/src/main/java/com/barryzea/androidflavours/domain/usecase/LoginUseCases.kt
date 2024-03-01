package com.barryzea.androidflavours.domain.usecase

import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.entities.CreateSessionRequest
import com.barryzea.androidflavours.data.entities.PostResponse
import com.barryzea.androidflavours.data.entities.RequestToken
import com.barryzea.androidflavours.data.entities.Session
import com.barryzea.androidflavours.data.entities.User
import com.barryzea.androidflavours.domain.entities.DomainAuth
import com.barryzea.androidflavours.data.entities.ValidateLoginRequest

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/

interface LoginUseCases {
    suspend fun getRequestToken(): TmdbResponse<RequestToken>
    suspend fun validateWithLogin(request: ValidateLoginRequest): TmdbResponse<RequestToken>
    suspend fun createSession(request: CreateSessionRequest): TmdbResponse<Session>
    suspend fun fetchUserDetails(sessionId:String):TmdbResponse<User>
    suspend fun logout(sessionId:String):TmdbResponse<PostResponse>

}