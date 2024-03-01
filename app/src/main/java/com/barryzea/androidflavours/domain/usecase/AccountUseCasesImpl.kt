package com.barryzea.androidflavours.domain.usecase

import android.util.Log
import com.barryzea.androidflavours.common.handleRequest
import com.barryzea.androidflavours.data.entities.PostResponse
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.entities.TmdbResult
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
    ): TmdbResponse<TmdbResult> {
        return handleRequest { accountRepository.fetchMyFavoriteMovies(accountId,sessionId,page) }
    }

    override suspend fun fetchMyWatchlistMovies(
        accountId: String,
        sessionId: String,
        page: Int
    ): TmdbResponse<TmdbResult> {
        return handleRequest { accountRepository.fetchMyWatchlistMovies(accountId,sessionId,page) }
    }

    override suspend fun addToFavorite(
        accountId: Int,
        sessionId: String,
        idMovie: Int
    ): TmdbResponse<PostResponse> {
        return handleRequest { accountRepository.addToFavorite(apiKey, accountId,sessionId,idMovie) }
    }

    override suspend fun addToWatchlist(
        accountId: Int,
        sessionId: String,
        idMovie: Int
    ): TmdbResponse<PostResponse> {
       return handleRequest { accountRepository.addToWatchlist(apiKey, accountId,sessionId,idMovie) }
    }
}