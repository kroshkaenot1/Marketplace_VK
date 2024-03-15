package com.template.marketplace_vk.domain.repository

import com.template.marketplace_vk.data.models.CategoriesResult
import com.template.marketplace_vk.data.models.ProductsResult
import com.template.marketplace_vk.utils.Constants

interface ProductsRepository {
    suspend fun getProducts(
        skip: Int = 0,
        limit: Int = Constants.productsLoadQuantity
    ): ProductsResult

    suspend fun searchProducts(
        skip: Int = 0,
        limit: Int = Constants.productsLoadQuantity,
        name: String
    ): ProductsResult

    suspend fun getCategories(): CategoriesResult

    suspend fun getProductsByCategory(category: String): ProductsResult
}