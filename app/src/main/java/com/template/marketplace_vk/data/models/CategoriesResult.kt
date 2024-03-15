package com.template.marketplace_vk.data.models

import com.template.marketplace_vk.domain.model.Category

sealed class CategoriesResult {
    data class Success(val categories: List<Category>) : CategoriesResult()
    data class Error(val message: String) : CategoriesResult()
}