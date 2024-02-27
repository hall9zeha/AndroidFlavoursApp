package com.barryzea.androidflavours.data.entities
import com.google.gson.annotations.SerializedName

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/
data class RequestToken(
    @SerializedName("success")val success:Boolean=false,
    @SerializedName("expires_at")val expiresAt:String?="",
    @SerializedName("request_token")val requestToken:String?=""
)
data class Session(
    @SerializedName("success")val success: Boolean = false,
    @SerializedName("session_id")val sessionId:String?=""
)
data class User(
    @SerializedName("avatar")val avatar:Avatar=Avatar(),
    @SerializedName("id")val id:Int=0,
    @SerializedName("iso_639_1")val language:String?="",
    @SerializedName("iso_3166_1")val country:String?="",
    @SerializedName("name")val name:String?="",
    @SerializedName("include_adult")val includeAdult:Boolean=false,
    @SerializedName("username")val username:String?="",

)
data class Avatar(
    @SerializedName("tmdb")val avatarTmdb:Tmdb= Tmdb()
)
data class Tmdb(@SerializedName("avatar_path")val avatarPath:String?="")
data class PostResponse(@SerializedName("success")val success:Boolean=false)