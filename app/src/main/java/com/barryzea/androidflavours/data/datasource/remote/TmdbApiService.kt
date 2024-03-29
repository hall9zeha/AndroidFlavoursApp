package com.barryzea.androidflavours.data.datasource.remote

import com.barryzea.androidflavours.data.entities.Cast
import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.data.entities.TmdbResult
import com.barryzea.androidflavours.data.entities.Trailers
import com.barryzea.androidflavours.domain.entities.DomainMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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
        @Query("language")language: String,
        @Query("page")page:Int?
    ):Response<TmdbResult>

    @GET("genre/movie/list")
    suspend fun fetchGenres(
        @Query("api_key")apiKey:String,
        @Query("language")language:String
    ):Response<Genres>

    //Obtenemos las películas ordenadas por:
    //Más populares(Popular), mejor calificadas(Top rated), en estreno(Play now), próximas a estrenar (Upcoming)
    @GET("movie/{sortValue}")
    suspend fun fetchMovieSortedBy(
        @Path("sortValue")sortValue:String,
        @Query("api_key")apiKey: String,
        @Query("language")language:String,
        @Query("page")page:Int
    ):Response<TmdbResult>

    @GET("movie/{idMovie}/credits")
    suspend fun fetchCredits(
        @Path("idMovie")idMovie:Int,
        @Query("api_key")apiKey: String
    ):Response<Cast>

    @GET("movie/{idMovie}/videos")
    suspend fun fetchTrailers(
        @Path("idMovie")idMovie:Int,
        @Query("api_key")apiKey: String
    ):Response<Trailers>

}