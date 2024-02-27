package com.barryzea.androidflavours.data.repository

import com.barryzea.androidflavours.data.entities.PostResponse
import com.barryzea.androidflavours.data.entities.TmdbResult
import retrofit2.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 20/02/2024.
 **/
interface AccountRepository {
    suspend fun fetchMyFavoriteMovies(accountId:String,sessionId:String,page:Int):Response<TmdbResult>
    suspend fun fetchMyWatchlistMovies(accountId:String,sessionId:String,page:Int):Response<TmdbResult>
    suspend fun addToFavorite(apiKey:String,accountId: Int,sessionId: String, idMovie:Int):Response<PostResponse>
}