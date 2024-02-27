package com.barryzea.androidflavours.domain.usecase

import android.util.Log
import com.barryzea.androidflavours.data.entities.PostResponse
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.repository.AccountRepository
import com.barryzea.androidflavours.domain.entities.DomainAuth
import com.barryzea.androidflavours.domain.entities.DomainMovie
import com.barryzea.androidflavours.domain.entities.toDomain

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 20/02/2024.
 **/

class AccountUseCasesImpl(private val accountRepository: AccountRepository, private val apiKey:String):AccountUseCases {
    override suspend fun fetchMyFavoriteMovies(
        accountId: String,
        sessionId:String,
        page: Int
    ): TmdbResponse<DomainMovie> {
        return try {
            val response = accountRepository.fetchMyFavoriteMovies(accountId,sessionId,page)
            if(response.isSuccessful) TmdbResponse.Success(response.body()?.toDomain()!!)
            else TmdbResponse.Error(response.message())

        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }

    override suspend fun fetchMyWatchlistMovies(
        accountId: String,
        sessionId: String,
        page: Int
    ): TmdbResponse<DomainMovie> {
        return try {
            val response = accountRepository.fetchMyWatchlistMovies(accountId,sessionId,page)

            if(response.isSuccessful) TmdbResponse.Success(response.body()?.toDomain()!!)
            else TmdbResponse.Error(response.message())

        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }

    override suspend fun addToFavorite(
        accountId: Int,
        sessionId: String,
        idMovie: Int
    ): TmdbResponse<DomainAuth> {
        return try{
            val response = accountRepository.addToFavorite(apiKey, accountId,sessionId,idMovie)
            if(response.isSuccessful)TmdbResponse.Success(response.body()!!.toDomain())
            else TmdbResponse.Error(response.message())
        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }

    override suspend fun addToWatchlist(
        accountId: Int,
        sessionId: String,
        idMovie: Int
    ): TmdbResponse<DomainAuth> {
        return try{
            val response = accountRepository.addToWatchlist(apiKey, accountId,sessionId,idMovie)
            if(response.isSuccessful)TmdbResponse.Success(response.body()!!.toDomain())
            else TmdbResponse.Error(response.message())
        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }
}