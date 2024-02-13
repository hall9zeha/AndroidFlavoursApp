package com.barryzea.androidflavours.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 30/01/2024.
 **/

@Parcelize
data class TmdbResult(
    val dates:Dates = Dates(),
    val pages:Int=0,
    @SerializedName("results")val movies:ArrayList<TmdbMovie> = arrayListOf<TmdbMovie>(),
    @SerializedName("total_pages")val total_pages:Int=0,
    @SerializedName("total_results")val total_result:Int=0):Parcelable

@Parcelize
data class Dates(val maximum:String="", val minimum:String=""):Parcelable
@Parcelize
data class Genres(
    @SerializedName("genres")val genres:List<Genre> = arrayListOf()
):Parcelable

@Parcelize
data class Genre(
    @SerializedName("id") val id:Int = 0,
    @SerializedName("name")val name:String=""
):Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Genre
        return id == other.id
    }
    override fun hashCode(): Int {
        return id
    }
}

data class Cast(
    @SerializedName("id")val id:Int=0,
    @SerializedName("cast")val cast:List<CharacterMovie> = listOf()
)
data class Trailers(
    @SerializedName("id")val id:Int=0,
    @SerializedName("results")val trailers:List<TrailerMovie> = listOf()
)