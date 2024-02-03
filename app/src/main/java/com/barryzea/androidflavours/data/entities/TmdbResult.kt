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
    val pages:Int=0,
    @SerializedName("results")val movies:ArrayList<TmdbMovie> = arrayListOf<TmdbMovie>(),
    @SerializedName("total_pages")val total_pages:Int=0,
    @SerializedName("total_result")val total_result:Int=0):Parcelable
