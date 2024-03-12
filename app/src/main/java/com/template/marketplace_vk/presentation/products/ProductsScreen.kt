package com.template.marketplace_vk.presentation.products

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.template.marketplace_vk.data.models.Product
import com.template.marketplace_vk.presentation.product.ProductDetails
import com.template.marketplace_vk.presentation.products.components.ProductCard

@Composable
fun Products(
    modifier: Modifier = Modifier,
    productsViewModel: ProductsViewModel = hiltViewModel(),
) {
    val lazyGridState = rememberLazyGridState()
    val listOfProducts = productsViewModel.listOfProducts.collectAsState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cellWidth = 181.dp
    val columnCount = (screenWidth / cellWidth).toInt()
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    if (listOfProducts.value.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = modifier.size(50.dp))
        }
    } else {
        LazyVerticalGrid(
            modifier = modifier.fillMaxSize(),
            columns = GridCells.Fixed(columnCount),
            state = lazyGridState
        ) {
            items(listOfProducts.value.size) { index ->
                if (index == listOfProducts.value.size - 1
                    && lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == index
                ) {
                    productsViewModel.getProducts()
                }
                ProductCard(product = listOfProducts.value[index],
                    onProductClick = { product ->
                        selectedProduct = product
                    })
            }
        }
    }
    AnimatedVisibility(
        visible = selectedProduct != null,
        enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(800)),
        exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(500)),
    ) {
        selectedProduct?.let { product ->
            ProductDetails(
                product = product,
                onClose = { selectedProduct = null }
            )
        }
    }
}