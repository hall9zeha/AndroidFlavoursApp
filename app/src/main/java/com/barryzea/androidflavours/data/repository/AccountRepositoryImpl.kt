package com.barryzea.androidflavours.data.repository

import com.barryzea.androidflavours.data.datasource.remote.RetrofitService
import com.barryzea.androidflavours.data.entities.PostResponse
import com.barryzea.androidflavours.data.entities.TmdbResult
import retrofit2.Response
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 20/02/2024.
 **/

class AccountRepositoryImpl @Inject constructor(retrofit:RetrofitService,
                                                private val apiKey:String,
                                                private val lang:String):AccountRepository {
    private val accountService = retrofit.accountService()
    override suspend fun fetchMyFavoriteMovies(
        accountId: String,
        sessionId:String,
        page: Int) =accountService.fetchMyFavoriteMovies(accountId,apiKey,sessionId,lang,page)
    override suspend fun fetchMyWatchlistMovies(
        accountId: String,
        sessionId: String,
        page: Int
    )= accountService.fetchMyWatchlistMovies(accountId,apiKey,sessionId,lang,page)

    override suspend fun addToFavorite(
        apiKey: String,
        accountId: Int,
        sessionId: String,
        idMovie: Int
    ) = accountService.addToFavorites(apiKey=apiKey, accountId = accountId, sessionId = sessionId, mediaId = idMovie)

    override suspend fun addToWatchlist(
        apiKey: String,
        accountId: Int,
        sessionId: String,
        idMovie: Int
    ) = accountService.addToWatchlist(apiKey = apiKey, accountId = accountId, sessionId = sessionId, mediaId = idMovie)
}