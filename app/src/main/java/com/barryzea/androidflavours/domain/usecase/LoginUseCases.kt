package com.barryzea.androidflavours.domain.usecase

import com.barryzea.androidflavours.data.entities.RequestToken
import com.barryzea.androidflavours.data.entities.Session
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.domain.entities.CreateSessionRequest
import com.barryzea.androidflavours.domain.entities.DomainAuth
import com.barryzea.androidflavours.domain.entities.ValidateLoginRequest
import retrofit2.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/

interface LoginUseCases {
    suspend fun getRequestToken(): TmdbResponse<DomainAuth>
    suspend fun validateWithLogin(request: ValidateLoginRequest): TmdbResponse<DomainAuth>
    suspend fun createSession(request: CreateSessionRequest): TmdbResponse<DomainAuth>
    suspend fun fetchUserDetails(sessionId:String):TmdbResponse<DomainAuth>
    suspend fun logout(sessionId:String):TmdbResponse<DomainAuth>

}