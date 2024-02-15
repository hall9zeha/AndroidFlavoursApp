package com.barryzea.androidflavours.domain.usecase

import com.barryzea.androidflavours.data.entities.RequestToken
import com.barryzea.androidflavours.data.entities.Session
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.repository.LoginRepository
import com.barryzea.androidflavours.domain.entities.CreateSessionRequest
import com.barryzea.androidflavours.domain.entities.ValidateLoginRequest

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/

class LoginUseCasesImpl(private val loginRepository: LoginRepository):LoginUseCases {
    override suspend fun getRequestToken(): TmdbResponse<RequestToken> {
        TODO("Not yet implemented")
    }

    override suspend fun validateWithLogin(request: ValidateLoginRequest): TmdbResponse<RequestToken> {
        TODO("Not yet implemented")
    }

    override suspend fun createSession(request: CreateSessionRequest): TmdbResponse<Session> {
        TODO("Not yet implemented")
    }
}