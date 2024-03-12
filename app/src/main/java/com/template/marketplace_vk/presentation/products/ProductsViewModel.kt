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

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            val skip = _listOfProducts.value.size
            _listOfProducts.emit(
                _listOfProducts.value.plus(
                    productsRepository.getProducts(skip = skip).products
                )
            )
        }
    }
}