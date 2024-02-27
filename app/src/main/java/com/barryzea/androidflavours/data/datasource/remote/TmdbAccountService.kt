package com.barryzea.androidflavours.data.datasource.remote

import com.barryzea.androidflavours.data.entities.PostResponse
import com.barryzea.androidflavours.data.entities.TmdbResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 20/02/2024.
 **/
interface TmdbAccountService {
    @GET("account/{account_id}/favorite/movies")
    suspend fun fetchMyFavoriteMovies(
        @Path("account_id")accountId:String,
        @Query("api_key") apiKey:String,
        @Query("session_id")sessionId:String,
        @Query("language")language:String,
        @Query("page")page:Int
    ): Response<TmdbResult>
    @GET("account/{account_id}/watchlist/movies")
    suspend fun fetchMyWatchlistMovies(
        @Path("account_id")accountId:String,
        @Query("api_key") apiKey:String,
        @Query("session_id")sessionId:String,
        @Query("language")language:String,
        @Query("page")page:Int
    ): Response<TmdbResult>
    @POST("account/{account_id}/favorite")
    suspend fun addToFavorites(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("media_type") mediaType: String = "movie",
        @Query("media_id") mediaId: Int,
        @Query("favorite") favorite: Boolean = true
    ): Response<PostResponse>
}