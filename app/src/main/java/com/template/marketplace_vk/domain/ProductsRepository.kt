package com.template.marketplace_vk.domain

import com.template.marketplace_vk.data.models.ListOfProducts
import com.template.marketplace_vk.utils.Constants

interface ProductsRepository {
    suspend fun getProducts(
        skip: Int = 0,
        limit: Int = Constants.productsLoadQuantity
    ): ListOfProducts

    suspend fun searchProducts(
        skip: Int = 0,
        limit: Int = Constants.productsLoadQuantity,
        name: String
    ): ListOfProducts
}