package com.barryzea.androidflavours.data.datasource.remote

import com.barryzea.androidflavours.common.BASE_URL
import com.barryzea.androidflavours.common.utils.MyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/
//Api client
class RetrofitServiceImpl @Inject constructor(myInterceptor: MyInterceptor):RetrofitService {
    private val client = OkHttpClient.Builder().addInterceptor(myInterceptor).build()

    private fun retrofitInstance():Retrofit{
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(client)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }
    override fun retrofitService(): TmdbApiService = retrofitInstance().create(TmdbApiService::class.java)
    override fun loginService(): TmdbLoginService = retrofitInstance().create(TmdbLoginService::class.java)
    override fun accountService(): TmdbAccountService = retrofitInstance().create(TmdbAccountService::class.java)
}