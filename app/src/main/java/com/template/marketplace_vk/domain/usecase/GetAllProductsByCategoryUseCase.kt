package com.template.marketplace_vk.domain.usecase

import com.template.marketplace_vk.domain.repository.ProductsRepository
import javax.inject.Inject

class GetAllProductsByCategoryUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend fun execute(category: String) = productsRepository.getProductsByCategory(category)
}