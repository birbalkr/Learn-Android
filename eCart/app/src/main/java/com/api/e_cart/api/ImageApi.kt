package com.api.e_cart.api


import android.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {
    @GET("image/{size}/{color}")
    fun getImage(
        @Path("size") size: String,
        @Path("color") color: String,
        @Query("fontFamily") font: String,
        @Query("text") text: String
    ): Call<ResponseBody>
}