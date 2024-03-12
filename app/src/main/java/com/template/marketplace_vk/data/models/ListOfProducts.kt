package com.template.marketplace_vk.data.models

import com.google.gson.annotations.SerializedName

data class ListOfProducts(
    @SerializedName(value = "products")
    val products: List<Product>,
    @SerializedName(value = "total")
    val total: Long,
    @SerializedName(value = "skip")
    val skip: Long,
    @SerializedName(value = "limit")
    val limit: Long,
)
