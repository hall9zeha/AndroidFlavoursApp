package com.barryzea.androidflavours.data.datasource.remote

import android.provider.SyncStateContract.Constants
import com.barryzea.androidflavours.common.BASE_URL
import com.barryzea.androidflavours.common.utils.MyInterceptor
import com.barryzea.androidflavours.data.entities.TmdbResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/
//Api client
class RetrofitServiceImpl @Inject constructor():RetrofitService {
    private val client = OkHttpClient.Builder().addInterceptor(MyInterceptor()).build()

    private fun retrofitInstance():Retrofit{
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(client)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }
    override fun retrofitService(): TmdbApiService = retrofitInstance().create(TmdbApiService::class.java)
}