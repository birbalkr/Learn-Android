package com.api.e_cart.api


import com.api.e_cart.model.postData.PostData
import retrofit2.Call
import retrofit2.http.GET

interface PostApi {
    @GET("post")
    fun getPosts(): Call<PostData>
}