package com.barryzea.androidflavours.data.datasource.remote

import com.barryzea.androidflavours.data.entities.Genre
import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.data.entities.TmdbMovie
import com.barryzea.androidflavours.data.entities.TmdbResult
import com.barryzea.androidflavours.domain.entities.DomainMovie
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
    suspend fun fetchMovies(
        @Query("api_key")apiKey:String,
        @Query("with_genres")genreId:Int?,
        @Query("language")language:String,
        @Query("page")page:Int
    ):Response<TmdbResult>
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") searchValue:String,
        @Query("api_key") apiKey:String,
        @Query("language")language: String
    ):Response<DomainMovie>

    @GET("genre/movie/list")
    suspend fun fetchGenres(
        @Query("api_key")apiKey:String,
        @Query("language")language:String
    ):Response<Genres>

}