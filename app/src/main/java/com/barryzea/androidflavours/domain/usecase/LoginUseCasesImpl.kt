package com.barryzea.androidflavours.domain.usecase

import android.util.Log
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.repository.LoginRepository
import com.barryzea.androidflavours.domain.entities.CreateSessionRequest
import com.barryzea.androidflavours.domain.entities.DomainAuth
import com.barryzea.androidflavours.domain.entities.ValidateLoginRequest
import com.barryzea.androidflavours.domain.entities.toDomain
import retrofit2.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/

class LoginUseCasesImpl(private val loginRepository: LoginRepository):LoginUseCases {
    override suspend fun getRequestToken(): TmdbResponse<DomainAuth> {
        return try {
            val response = loginRepository.getRequestToken()
            if(response.isSuccessful) TmdbResponse.Success(response.body()?.toDomain()!!)
            else TmdbResponse.Error(response.message())

        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }
    override suspend fun validateWithLogin(request: ValidateLoginRequest): TmdbResponse<DomainAuth> {
        return try {
            val response = loginRepository.validateWithLogin(request)
            if(response.isSuccessful) TmdbResponse.Success(response.body()?.toDomain()!!)
            else TmdbResponse.Error(response.message())
        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }

    override suspend fun createSession(request: CreateSessionRequest): TmdbResponse<DomainAuth> {
        return try {
            val response = loginRepository.createSession(request)
            if(response.isSuccessful) TmdbResponse.Success(response.body()?.toDomain()!!)
            else TmdbResponse.Error(response.message())
        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }
    override suspend fun fetchUserDetails(sessionId: String): TmdbResponse<DomainAuth> {
        return try {
            val response = loginRepository.fetchUserDetails(sessionId)
            if(response.isSuccessful) TmdbResponse.Success(response.body()?.toDomain()!!)
            else TmdbResponse.Error(response.message())
        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }
    override suspend fun logout(sessionId: String): TmdbResponse<DomainAuth> {
        return try {
            val response = loginRepository.logout(sessionId)
            if(response.isSuccessful) TmdbResponse.Success(response.body()?.toDomain()!!)
            else TmdbResponse.Error(response.message())
        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }
}