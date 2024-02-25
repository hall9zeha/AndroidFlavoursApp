package com.barryzea.androidflavours.domain.usecase
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.domain.entities.DomainMovie

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 20/02/2024.
 **/
interface AccountUseCases {
    suspend fun fetchMyFavoriteMovies(accountId:String,sessionId:String, page:Int):TmdbResponse<DomainMovie>
    suspend fun fetchMyWatchlistMovies(accountId:String,sessionId:String, page:Int):TmdbResponse<DomainMovie>
}