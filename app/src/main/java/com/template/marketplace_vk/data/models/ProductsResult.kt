package com.template.marketplace_vk.data.models

sealed class ProductsResult {
    data class Success(val products: List<Product>) : ProductsResult()
    data class Error(val message: String) : ProductsResult()
}