package com.barryzea.androidflavours.domain.usecase

import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.repository.AccountRepository
import com.barryzea.androidflavours.domain.entities.DomainMovie
import com.barryzea.androidflavours.domain.entities.toDomain

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 20/02/2024.
 **/

class AccountUseCasesImpl(private val accountRepository: AccountRepository):AccountUseCases {
    override suspend fun fetchMyFavoriteMovies(
        accountId: String,
        sessionId:String,
        page: Int
    ): TmdbResponse<DomainMovie> {
        return try {
            val response = accountRepository.fetchMyFavoriteMovies(accountId,sessionId,page)
            if(response.isSuccessful) TmdbResponse.Success(response.body()?.toDomain()!!)
            else TmdbResponse.Error(response.message())

        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }
}