package com.template.marketplace_vk.data.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName(value = "id")
    val id: Long,
    @SerializedName(value = "title")
    val title: String,
    @SerializedName(value = "description")
    val description: String,
    @SerializedName(value = "price")
    val price: Long,
    @SerializedName(value = "discountPercentage")
    val discountPercentage: Double,
    @SerializedName(value = "rating")
    val rating: Double,
    @SerializedName(value = "stock")
    val stock: Long,
    @SerializedName(value = "brand")
    val brand: String,
    @SerializedName(value = "category")
    val category: String,
    @SerializedName(value = "thumbnail")
    val thumbnail: String,
    @SerializedName(value = "images")
    var images: List<String>,
)