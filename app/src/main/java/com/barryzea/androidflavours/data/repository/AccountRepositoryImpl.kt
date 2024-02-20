package com.barryzea.androidflavours.data.repository

import com.barryzea.androidflavours.data.datasource.remote.RetrofitService
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 20/02/2024.
 **/

class AccountRepositoryImpl @Inject constructor(retrofit:RetrofitService,
                                                private val apiKey:String,
                                                private val lang:String):AccountRepository {
    private val accountService = retrofit.accountService()
    override suspend fun fetchMyFavoriteMovies(accountId: String, page: Int) =accountService.fetchMyFavoriteMovies(accountId,apiKey,lang,page)
}