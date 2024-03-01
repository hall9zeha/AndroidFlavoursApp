package com.barryzea.androidflavours.domain.usecase

import com.barryzea.androidflavours.common.handleRequest
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.repository.LoginRepository
import com.barryzea.androidflavours.data.entities.CreateSessionRequest
import com.barryzea.androidflavours.data.entities.PostResponse
import com.barryzea.androidflavours.data.entities.RequestToken
import com.barryzea.androidflavours.data.entities.Session
import com.barryzea.androidflavours.data.entities.User
import com.barryzea.androidflavours.domain.entities.DomainAuth
import com.barryzea.androidflavours.data.entities.ValidateLoginRequest
import com.barryzea.androidflavours.domain.entities.toDomain

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/

class LoginUseCasesImpl(private val loginRepository: LoginRepository):LoginUseCases {
    override suspend fun getRequestToken(): TmdbResponse<RequestToken> {
       return handleRequest { loginRepository.getRequestToken() }
    }
    override suspend fun validateWithLogin(request: ValidateLoginRequest): TmdbResponse<RequestToken> {
       return handleRequest { loginRepository.validateWithLogin(request) }
    }
    override suspend fun createSession(request: CreateSessionRequest): TmdbResponse<Session> {
       return handleRequest { loginRepository.createSession(request) }
    }
    override suspend fun fetchUserDetails(sessionId: String): TmdbResponse<User> {
       return handleRequest { loginRepository.fetchUserDetails(sessionId) }
    }
    override suspend fun logout(sessionId: String): TmdbResponse<PostResponse> {
       return handleRequest {  loginRepository.logout(sessionId)}
    }
}