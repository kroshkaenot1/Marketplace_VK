package com.template.marketplace_vk.domain.usecase

import com.template.marketplace_vk.domain.repository.ProductsRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend fun execute() = productsRepository.getCategories()
}