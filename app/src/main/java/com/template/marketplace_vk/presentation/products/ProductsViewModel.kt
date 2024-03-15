package com.template.marketplace_vk.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.marketplace_vk.data.models.CategoriesResult
import com.template.marketplace_vk.data.models.Product
import com.template.marketplace_vk.data.models.ProductsResult
import com.template.marketplace_vk.domain.model.Category
import com.template.marketplace_vk.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _listOfProducts: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
    val listOfProducts = _listOfProducts.asStateFlow()

    private val _isSearchInProgress: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSearchInProgress = _isSearchInProgress.asStateFlow()

    private val _listOfCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val listOfCategories = _listOfCategories.asStateFlow()

    private val _error: MutableStateFlow<String> = MutableStateFlow("")
    val error = _error.asStateFlow()

    init {
        fetchProducts()
        fetchCategories()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _isSearchInProgress.emit(true)
            val skip = _listOfProducts.value.size
            when (val productsResult = productsRepository.getProducts(skip = skip)) {
                is ProductsResult.Error -> {
                    _error.emit(productsResult.message)
                }

                is ProductsResult.Success -> {
                    _listOfProducts.emit(
                        _listOfProducts.value.plus(
                            productsResult.products
                        )
                    )
                }
            }
            _isSearchInProgress.emit(false)
        }
    }

    fun searchProducts(name: String) {
        viewModelScope.launch {
            _isSearchInProgress.emit(true)
            val skip = _listOfProducts.value.size
            when (val productsResult =
                productsRepository.searchProducts(skip = skip, name = name)) {
                is ProductsResult.Error -> {
                    _error.emit(productsResult.message)
                }

                is ProductsResult.Success -> {
                    _listOfProducts.emit(
                        _listOfProducts.value.plus(
                            productsResult.products
                        )
                    )
                }
            }
            _isSearchInProgress.emit(false)
        }
    }

    fun clearProductsList() {
        viewModelScope.launch {
            _listOfProducts.emit(emptyList())
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            when (val categoriesResult = productsRepository.getCategories()) {
                is CategoriesResult.Error -> {}
                is CategoriesResult.Success -> {
                    _listOfCategories.emit(
                        categoriesResult.categories
                    )
                }
            }
        }
    }

    fun fetchProductsByCategory(categories: List<String>) {
        viewModelScope.launch {
            _isSearchInProgress.emit(true)
            clearProductsList()
            categories.forEach { category ->
                when (val productsResult =
                    productsRepository.getProductsByCategory(category = category)) {
                    is ProductsResult.Error -> {
                        _error.emit(productsResult.message)
                    }

                    is ProductsResult.Success -> {
                        _listOfProducts.emit(
                            _listOfProducts.value.plus(
                                productsResult.products
                            )
                        )
                    }
                }
            }
            _isSearchInProgress.emit(false)
        }
    }

    fun clearSelectedCategories() {
        _listOfCategories.value.forEach { category ->
            category.isSelected.value = false
        }
    }
}