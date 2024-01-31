package com.barryzea.androidflavours.data.repository

import com.barryzea.androidflavours.data.entities.TmdbResult
import retrofit2.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/
interface Repository {
    suspend fun fetchMovies(page:Int):Response<TmdbResult>
}