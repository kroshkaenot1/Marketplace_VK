package com.template.marketplace_vk.data.repositoryimpl

import com.template.marketplace_vk.data.models.CategoriesResult
import com.template.marketplace_vk.data.models.ProductsResult
import com.template.marketplace_vk.data.remote.api.APIProducts
import com.template.marketplace_vk.domain.model.Category
import com.template.marketplace_vk.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val apiProducts: APIProducts
) : ProductsRepository {
    override suspend fun getProducts(skip: Int, limit: Int): ProductsResult {
        return try {
            val response = apiProducts.getProducts(skip, limit)
            ProductsResult.Success(response.products)
        } catch (e: Exception) {
            ProductsResult.Error(e.message ?: "Неизвестная ошибка")
        }
    }

    override suspend fun searchProducts(skip: Int, limit: Int, name: String): ProductsResult {
        return try {
            val response = apiProducts.searchProducts(skip = skip, limit = limit, name = name)
            ProductsResult.Success(response.products)
        } catch (e: Exception) {
            ProductsResult.Error(e.message ?: "Неизвестная ошибка")
        }
    }

    override suspend fun getCategories(): CategoriesResult {
        return try {
            val response = apiProducts.getCategories()
            val result = response.map { categoryName ->
                Category(name = categoryName)
            }
            CategoriesResult.Success(result)
        } catch (e: Exception) {
            CategoriesResult.Error(e.message ?: "Неизвестная ошибка")
        }
    }

    override suspend fun getProductsByCategory(category: String): ProductsResult {
        return try {
            val response = apiProducts.getProductsByCategory(category = category)
            ProductsResult.Success(response.products)
        } catch (e: Exception) {
            ProductsResult.Error(e.message ?: "Неизвестная ошибка")
        }
    }
}