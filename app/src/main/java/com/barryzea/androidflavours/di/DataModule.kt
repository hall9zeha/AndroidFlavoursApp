package com.barryzea.androidflavours.di

import com.barryzea.androidflavours.data.repository.AccountRepository
import com.barryzea.androidflavours.data.repository.LoginRepository
import com.barryzea.androidflavours.data.repository.Repository
import com.barryzea.androidflavours.domain.usecase.AccountUseCases
import com.barryzea.androidflavours.domain.usecase.AccountUseCasesImpl
import com.barryzea.androidflavours.domain.usecase.MovieUseCasesImpl
import com.barryzea.androidflavours.domain.usecase.LoginUseCases
import com.barryzea.androidflavours.domain.usecase.LoginUseCasesImpl
import com.barryzea.androidflavours.domain.usecase.MovieUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {
    @ViewModelScoped
    @Provides
    fun useCaseProvides(repository:Repository):MovieUseCases = MovieUseCasesImpl(repository)

    @ViewModelScoped
    @Provides
    fun loginUseCaseProvides(loginRepository:LoginRepository):LoginUseCases = LoginUseCasesImpl(loginRepository)

    @ViewModelScoped
    @Provides
    fun accountUseCaseProvides(accountRepository: AccountRepository, @Named("apiKey")apiKey:String):AccountUseCases = AccountUseCasesImpl(accountRepository,apiKey)
}

