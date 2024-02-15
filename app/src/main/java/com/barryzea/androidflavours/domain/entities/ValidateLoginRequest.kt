package com.barryzea.androidflavours.domain.entities

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/
data class ValidateLoginRequest(
    val username:String?="",
    val password:String?="",
    val request_token:String?=""
)
data class CreateSessionRequest(
    val request_token: String?=""
)

