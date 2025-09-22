package com.api.e_cart.model.auth

data class UserAuth(
    val username: String,
    val password: String,
    val expiresInMins: Int = 60
)


data class LoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val accessToken: String
)
