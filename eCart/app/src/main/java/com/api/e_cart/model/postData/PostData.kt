package com.api.e_cart.model.postData

data class PostData(
    val limit: Int,
    val posts: List<Post>,
    val skip: Int,
    val total: Int
)