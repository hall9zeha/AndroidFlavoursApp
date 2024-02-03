package com.barryzea.androidflavours.data.datasource.remote

import com.barryzea.androidflavours.data.entities.TmdbResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/

//Api service
interface TmdbApiService {
    @GET("discover/movie")
    suspend fun listMovies(
        @Query("api_key") apiKey:String,
        @Query("page")page:Int,
        @Query("language")language:String
    ):Response<TmdbResult>
}