package com.template.marketplace_vk.presentation.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.template.marketplace_vk.data.models.Product
import com.template.marketplace_vk.presentation.product.components.ProductDescription
import com.template.marketplace_vk.presentation.product.components.ProductDetailsBottomBar
import com.template.marketplace_vk.presentation.product.components.ProductDetailsTopBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProductDetails(
    modifier: Modifier = Modifier,
    product: Product,
    onClose: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val allProductImages = mutableListOf<String>().apply {
        add(product.thumbnail)
        addAll(product.images)
    }
    Scaffold(
        topBar = {
            ProductDetailsTopBar(onClose = onClose)
        },
        bottomBar = {
            ProductDetailsBottomBar()
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            LazyRow(
                modifier = modifier
                    .height(200.dp)
            ) {
                items(allProductImages.size) { index ->
                    GlideImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .defaultMinSize(minWidth = screenWidth, minHeight = 400.dp),
                        model = allProductImages[index],
                        contentDescription = null,
                        transition = CrossFade
                    )
                }
            }
            Spacer(modifier = modifier.height(8.dp))
            ProductDescription(product = product)
        }
    }
}