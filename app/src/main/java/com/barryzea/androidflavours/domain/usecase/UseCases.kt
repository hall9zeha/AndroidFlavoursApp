package com.barryzea.androidflavours.domain.usecase

import com.barryzea.androidflavours.data.entities.TmdbResponse

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 * Copyright (c)  All rights reserved.
 **/
interface UseCases {
    suspend fun fetchMovies(page:Int):TmdbResponse
}