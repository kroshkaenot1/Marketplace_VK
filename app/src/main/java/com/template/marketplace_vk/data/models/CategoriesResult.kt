package com.template.marketplace_vk.data.models

sealed class CategoriesResult {
    data class Success(val categories: List<String>) : CategoriesResult()
    data class Error(val message: String) : CategoriesResult()
}