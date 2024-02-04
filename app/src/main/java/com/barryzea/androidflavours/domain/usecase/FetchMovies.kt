package com.barryzea.androidflavours.domain.usecase

import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.repository.Repository
import com.barryzea.androidflavours.domain.entities.toDomain

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/

class FetchMovies(private val repository: Repository):UseCases {

    override suspend fun fetchMovies(page: Int) :TmdbResponse{
        return try {
            val response = repository.fetchMovies(page)
            if (response.isSuccessful){ TmdbResponse.Success(response.body()!!.toDomain())}
            else{ TmdbResponse.Error(response.message())}

          }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }

    override suspend fun searchMovie(searchValue: String): TmdbResponse {
        return try {
            val response = repository.searchMovie(searchValue)
            if(response.isSuccessful){TmdbResponse.Success(response.body()!!)}
            else{TmdbResponse.Error(response.message())}

        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }
}