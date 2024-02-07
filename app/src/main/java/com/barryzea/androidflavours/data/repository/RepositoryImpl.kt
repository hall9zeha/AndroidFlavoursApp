package com.barryzea.androidflavours.data.repository

import com.barryzea.androidflavours.data.datasource.remote.RetrofitService
import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.data.entities.TmdbResult
import com.barryzea.androidflavours.domain.entities.DomainMovie
import retrofit2.Response
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/

class RepositoryImpl @Inject constructor(retrofit: RetrofitService, private val apiKey: String, private val lang:String):Repository {
    private val apiService = retrofit.retrofitService()
    override suspend fun fetchMovies(
        genreId: Int?,
        page: Int,

    ):Response<TmdbResult> {
        return apiService.fetchMovies(apiKey,genreId,lang,page)
    }
    override suspend fun searchMovie(searchValue: String): Response<DomainMovie> {
        return  apiService.searchMovie(searchValue,apiKey,lang)
    }
    override suspend fun fetchGenres(): Response<Genres> {
        return apiService.fetchGenres(apiKey,lang)
    }

}