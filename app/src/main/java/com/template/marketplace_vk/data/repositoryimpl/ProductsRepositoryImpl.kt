package com.template.marketplace_vk.data.repositoryimpl

import com.template.marketplace_vk.data.models.ListOfProducts
import com.template.marketplace_vk.data.remote.APIProducts
import com.template.marketplace_vk.domain.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val apiProducts: APIProducts
) : ProductsRepository {
    override suspend fun getProducts(skip: Int, limit: Int): ListOfProducts =
        apiProducts.getProducts(skip = skip, limit = limit)

    override suspend fun searchProducts(skip: Int, limit: Int, name: String): ListOfProducts =
        apiProducts.searchProducts(skip = skip, limit = limit, name = name)

}