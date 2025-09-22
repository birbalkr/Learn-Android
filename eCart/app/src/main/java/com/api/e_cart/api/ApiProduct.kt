package com.api.e_cart.api

import com.api.e_cart.model.ProductData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiProduct {
    @GET("products")
    fun getProduct(): Call<ProductData>

    @GET("products/search")
    fun searchProduct(
        @Query("q") query: String
    ): Call<ProductData>

}