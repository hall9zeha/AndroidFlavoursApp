package com.barryzea.androidflavours.data.repository

import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.data.entities.TmdbResult
import com.barryzea.androidflavours.domain.entities.DomainMovie
import retrofit2.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/
interface Repository {
    suspend fun fetchMovies(page:Int):Response<TmdbResult>
    suspend fun searchMovie(searchValue:String):Response<DomainMovie>
    suspend fun fetchGenres():Response<Genres>
}