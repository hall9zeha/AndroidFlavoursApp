package com.barryzea.androidflavours.data.entities

import com.google.gson.annotations.SerializedName

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 11/02/2024.
 **/

data class CharacterMovie(
    @SerializedName("adult") val adult:Boolean=false,
    @SerializedName("gender") val gender:Int=0,
    @SerializedName("id") val id:Int=0,
    @SerializedName("known_for_department") val knowForDepartment:String="",
    @SerializedName("name") val name:String="",
    @SerializedName("original_name") val originalName:String="",
    @SerializedName("popularity") val popularity:Float=0F,
    @SerializedName("profile_path") val profilePath:String="",
    @SerializedName("cast_id") val castId:Int=0,
    @SerializedName("character") val character:String="",
    @SerializedName("credit_id") val creditId:String="",

    @SerializedName("order") val order:Int=0
        ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharacterMovie

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}