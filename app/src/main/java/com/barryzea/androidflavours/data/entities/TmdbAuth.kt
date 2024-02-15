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
