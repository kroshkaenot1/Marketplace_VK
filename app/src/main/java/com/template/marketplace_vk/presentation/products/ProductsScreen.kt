package com.template.marketplace_vk.presentation.products

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.template.marketplace_vk.R
import com.template.marketplace_vk.data.models.Product
import com.template.marketplace_vk.presentation.product.ProductDetails
import com.template.marketplace_vk.presentation.products.components.ProductCard
import com.template.marketplace_vk.presentation.products.components.TopAppBar
import com.template.marketplace_vk.presentation.splash.SplashScreen
import com.template.marketplace_vk.presentation.utils.SearchBarStates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Products(
    modifier: Modifier = Modifier,
    productsViewModel: ProductsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val isSearchInProgress = productsViewModel.isSearchInProgress.collectAsState()
    val listOfProducts = productsViewModel.listOfProducts.collectAsState()
    val lazyGridState = rememberLazyGridState()
    val searchBarState = remember {
        mutableStateOf(SearchBarStates.EMPTY)
    }
    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cellWidth = 181.dp
    val columnCount = (screenWidth / cellWidth).toInt()
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    Scaffold(
        topBar = { TopAppBar(searchBarState = searchBarState, textState = textState) }
    ) { paddingValues ->
        if (listOfProducts.value.isEmpty()) {
            if (!isSearchInProgress.value) {
                Text(
                    modifier = modifier
                        .padding(paddingValues = paddingValues)
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    text = context.getString(R.string.nothing_was_found)
                )
            } else {
                SplashScreen()
            }

        } else {
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                when (event.type) {
                                    PointerEventType.Press -> { focusManager.clearFocus() }
                                    else -> { focusManager.clearFocus() }
                                }
                            }
                        }
                    },
                columns = GridCells.Fixed(columnCount),
                state = lazyGridState
            ) {
                items(listOfProducts.value.size) { index ->
                    if ((index == listOfProducts.value.size - 1
                                && lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == index)
                        && !isSearchInProgress.value
                    ) {
                        if (searchBarState.value == SearchBarStates.SEARCHING) {
                            productsViewModel.searchProducts(name = textState.value.text)
                        } else {
                            productsViewModel.getProducts()
                        }
                    }
                    ProductCard(product = listOfProducts.value[index],
                        onProductClick = { product ->
                            selectedProduct = product
                        })
                }
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
            focusManager.clearFocus()
        }
    }
}