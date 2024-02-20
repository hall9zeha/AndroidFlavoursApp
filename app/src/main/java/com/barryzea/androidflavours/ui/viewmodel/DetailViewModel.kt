package com.barryzea.androidflavours.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.androidflavours.common.utils.SingleMutableLiveData
import com.barryzea.androidflavours.data.entities.CharacterMovie
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.entities.TrailerMovie
import com.barryzea.androidflavours.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 13/02/2024.
 **/

@HiltViewModel
class DetailViewModel @Inject constructor(private val useCases: UseCases) :ViewModel() {

    private var _credits:SingleMutableLiveData<List<CharacterMovie>> = SingleMutableLiveData()
    val credits:LiveData<List<CharacterMovie>> = _credits
    private var _infoMsg:MutableLiveData<String> = MutableLiveData()
    val infoMsg:LiveData<String> = _infoMsg

    //Estas propiedades no se usan en ninguna vista aún
    private var _trailers:MutableLiveData<List<TrailerMovie>> = MutableLiveData()
    val trailers:LiveData<List<TrailerMovie>> = _trailers

    //Podemos usar esta forma si queremos realizar peticiones  secuencialmente a nuestra API
   /* fun fetchMovieCredits(idMovie:Int){
        viewModelScope.launch {
            when(val response=useCases.fetchCredits(idMovie)){
                is TmdbResponse.Success->{_credits.value=response.tmdbResult}
                is TmdbResponse.Error->_infoMsg.value = response.msg
            }
        }
    }*/
    //Podemos usar esta forma para realizar multiples llamadas a nuestra API al mismo tiempo
    fun fetchCreditsAndTrailer(idMovie: Int){
        viewModelScope.launch {

            //Realizamos dos llamadas a la API simultaneamente
            val creditsDef = async { useCases.fetchCredits(idMovie) }
            val trailersDef = async { useCases.fetchTrailers(idMovie) }

            val (credits, trailers) = awaitAll(creditsDef, trailersDef)

            when {
                credits is TmdbResponse.Success ->{
                    //A causa de que nuestra clase TmdbResponse es de tipo genérico debemos castear al tipo  de dato esperado, según corresponda
                    _credits.value = credits.tmdbResult as List<CharacterMovie>
                }
                trailers is TmdbResponse.Success -> {

                    //Para reproducir los trailers de youtube se debe de obtener una key al registrar la app en nuestra cuenta
                    //de desarrollador e implementado la librería de youtube, ya que VideoView  no reproduce videos de youtube
                    //como si lo hacía antiguamente.
                    _trailers.value = trailers.tmdbResult as List<TrailerMovie>
                }

                credits is TmdbResponse.Error -> {
                    _infoMsg.value = credits.msg
                }

                trailers is TmdbResponse.Error -> {
                    _infoMsg.value=trailers.msg
                }
            }
        }}

}