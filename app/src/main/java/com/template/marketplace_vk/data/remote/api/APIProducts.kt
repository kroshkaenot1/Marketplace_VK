package com.template.marketplace_vk.data.remote.api

import com.template.marketplace_vk.data.models.ListOfProducts
import retrofit2.http.GET
import retrofit2.http.Query

interface APIProducts {
    @GET("products")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): ListOfProducts

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") name: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): ListOfProducts

    @GET("product/categories")
    suspend fun getCategories(): List<String>
}
