package com.barryzea.androidflavours.domain.entities

import com.google.gson.annotations.SerializedName

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/
data class ValidateLoginRequest(
    @SerializedName("username")val username:String?="",
    @SerializedName("password")val password:String?="",
    @SerializedName("request_token")val requestToken:String?=""
)
data class CreateSessionRequest(
    @SerializedName("request_token")val requestToken: String?=""
)

