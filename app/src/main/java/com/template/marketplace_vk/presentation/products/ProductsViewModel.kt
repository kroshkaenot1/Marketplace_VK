package com.template.marketplace_vk.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.marketplace_vk.data.models.Product
import com.template.marketplace_vk.domain.ProductsRepository
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

    private val _listOfCategories: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val listOfCategories = _listOfCategories.asStateFlow()

    init {
        getProducts()
        getCategories()
    }

    fun getProducts() {
        viewModelScope.launch {
            _isSearchInProgress.emit(true)
            val skip = _listOfProducts.value.size
            _listOfProducts.emit(
                _listOfProducts.value.plus(
                    productsRepository.getProducts(skip = skip).products
                )
            )
            _isSearchInProgress.emit(false)
        }
    }

    fun searchProducts(name: String) {
        viewModelScope.launch {
            _isSearchInProgress.emit(true)
            val skip = _listOfProducts.value.size
            _listOfProducts.emit(
                _listOfProducts.value.plus(
                    productsRepository.searchProducts(skip = skip, name = name).products
                )
            )
            _isSearchInProgress.emit(false)
        }
    }

    fun clearProductsList() {
        viewModelScope.launch {
            _listOfProducts.emit(emptyList())
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            _listOfCategories.emit(
                productsRepository.getCategories()
            )
        }
    }
}