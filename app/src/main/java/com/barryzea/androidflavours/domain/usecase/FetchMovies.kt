package com.barryzea.androidflavours.domain.usecase

import androidx.lifecycle.liveData
import com.barryzea.androidflavours.data.entities.CharacterMovie
import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.entities.TrailerMovie
import com.barryzea.androidflavours.data.repository.Repository
import com.barryzea.androidflavours.domain.entities.DomainMovie
import com.barryzea.androidflavours.domain.entities.toDomain
import kotlinx.coroutines.Job
import retrofit2.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/
class FetchMovies(private val repository: Repository):UseCases {
    override suspend fun fetchMovies(genreId:Int?,page: Int) =  try {
            val response = repository.fetchMovies(genreId,page)
            if (response.isSuccessful){ TmdbResponse.Success(response.body()!!.toDomain())}
            else{ TmdbResponse.Error(response.message())}
          }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    override suspend fun searchMovie(searchValue: String,page:Int?) = try {
            val response = repository.searchMovie(searchValue,page)
            if(response.isSuccessful){TmdbResponse.Success(response.body()!!)}
            else{TmdbResponse.Error(response.message())}

        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }

    override suspend fun fetchMoviesSortedBy(
        sortValue: String,
        page: Int
    ): TmdbResponse<DomainMovie> {
        return try{
            val response = repository.fetchMoviesSortedBy(sortValue,page)
            if(response.isSuccessful){TmdbResponse.Success(response.body()!!.toDomain())}
            else{TmdbResponse.Error(response.message())}
        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }

    override suspend fun fetchGenres(): TmdbResponse<Genres> {
        return try{
            val response = repository.fetchGenres()
            if(response.isSuccessful){TmdbResponse.Success(response.body()!!)}
            else{TmdbResponse.Error(response.message())}
        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }

    override suspend fun fetchCredits(idMovie: Int): TmdbResponse<List<CharacterMovie>> {
        return try{
            val response = repository.fetchCredits(idMovie)
            if(response.isSuccessful){TmdbResponse.Success(response.body()!!.cast)}
            else{TmdbResponse.Error(response.message())}
        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }

    }

    override suspend fun fetchTrailers(idMovie: Int): TmdbResponse<List<TrailerMovie>> {
        return try{
            val response = repository.fetchTrailers(idMovie)
            if(response.isSuccessful){TmdbResponse.Success(response.body()!!.trailers)}
            else{TmdbResponse.Error(response.message())}
        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }

  /*  private inline fun <reified  T : Any> handleResponse(fetchCall:()-> Response<T>):TmdbResponse<T>{
        return try{
            val response = fetchCall()
            if(response.isSuccessful)TmdbResponse.Success(response.body()!!)
            else TmdbResponse.Error(response.message())
        }catch(e:Exception){
            TmdbResponse.Error(e.message.toString())
        }
    }*/
}