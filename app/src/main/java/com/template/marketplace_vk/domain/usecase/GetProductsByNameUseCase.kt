package com.template.marketplace_vk.domain.usecase

import com.template.marketplace_vk.domain.repository.ProductsRepository
import com.template.marketplace_vk.utils.Constants
import javax.inject.Inject

class GetProductsByNameUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend fun execute(skip: Int, limit: Int = Constants.productsLoadQuantity, name: String) =
        productsRepository.searchProducts(skip = skip, limit = limit, name = name)
}