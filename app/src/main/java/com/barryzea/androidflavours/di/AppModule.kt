package com.barryzea.androidflavours.di

import android.app.Application
import android.content.Context
import com.barryzea.androidflavours.BuildConfig
import com.barryzea.androidflavours.common.utils.MyInterceptor
import com.barryzea.androidflavours.data.datasource.remote.RetrofitService
import com.barryzea.androidflavours.data.datasource.remote.RetrofitServiceImpl
import com.barryzea.androidflavours.data.datasource.remote.TmdbLoginService
import com.barryzea.androidflavours.data.repository.AccountRepository
import com.barryzea.androidflavours.data.repository.AccountRepositoryImpl
import com.barryzea.androidflavours.data.repository.LoginRepository
import com.barryzea.androidflavours.data.repository.LoginRepositoryImpl
import com.barryzea.androidflavours.data.repository.Repository
import com.barryzea.androidflavours.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import java.lang.StringBuilder
import java.util.Locale
import javax.inject.Named
import javax.inject.Singleton

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 30/01/2024.
 **/

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun contextProvides(app:Application):Context = app.applicationContext

    @Singleton
    @Provides
    fun interceptorProvides(context: Context):Interceptor = MyInterceptor(context)

    @Singleton
    @Provides
    @Named("apiKey")
    fun apiKeyProvides():String = BuildConfig.MY_API_KEY

    //TODO usar localización para obtener la región y lenguaje
    @Singleton
    @Provides
    @Named("language")
    fun languageProvides():String= Locale.getDefault().language.toString()


}

@Module
@InstallIn(SingletonComponent::class)
abstract class RetrofitModule{
    @Binds
    abstract fun retrofitProvides(retrofit:RetrofitServiceImpl): RetrofitService
}
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule{
    @Singleton
    @Provides
    fun repositoryProvides(
        retrofitService: RetrofitService,
        @Named("apiKey")apiKey:String,
        @Named("language")language:String): Repository=RepositoryImpl(retrofitService,apiKey,language)
    @Singleton
    @Provides
    fun loginRepositoryProvides(
        retrofitService: RetrofitService,
        @Named("apiKey")apiKey:String
    ):LoginRepository=LoginRepositoryImpl(retrofitService,apiKey)
    @Singleton()
    @Provides
    fun accountRepositoryProvides(
        retrofitService: RetrofitService,
        @Named("apiKey")apiKey:String,
        @Named("language")language:String
    ): AccountRepository =AccountRepositoryImpl(retrofitService,apiKey,language)
}