package com.barryzea.androidflavours.di

import com.barryzea.androidflavours.data.repository.Repository
import com.barryzea.androidflavours.domain.usecase.FetchMovies
import com.barryzea.androidflavours.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 * Copyright (c)  All rights reserved.
 **/

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun useCaseProvides(repository:Repository):UseCases = FetchMovies(repository)
}