package com.barryzea.androidflavours.di

import android.app.Application
import com.barryzea.androidflavours.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.lang.StringBuilder
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
    @Named("apiKey")
    fun apiKeyProvides():String = BuildConfig.MY_API_KEY

}