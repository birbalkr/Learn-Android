package com.api.e_cart.api

import com.api.e_cart.model.auth.LoginResponse
import com.api.e_cart.model.auth.UserAuth
import com.api.e_cart.model.auth.aboutMe.aboutMeData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("user/login")
    fun getLogin(
        @Body request: UserAuth
    ): Call<LoginResponse>

    @GET("/user/me")
    fun getAbout(
        @Header("Authorization") token: String
    ): Call<aboutMeData>
}