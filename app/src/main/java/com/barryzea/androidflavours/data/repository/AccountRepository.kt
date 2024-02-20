package com.barryzea.androidflavours.data.repository

import com.barryzea.androidflavours.data.entities.TmdbResult
import retrofit2.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 20/02/2024.
 **/
interface AccountRepository {
    suspend fun fetchMyFavoriteMovies(accountId:String,page:Int):Response<TmdbResult>
}