package com.barryzea.androidflavours.data.datasource.remote

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/
interface RetrofitService {
    fun retrofitService(): TmdbApiService
    fun loginService():TmdbLoginService
}