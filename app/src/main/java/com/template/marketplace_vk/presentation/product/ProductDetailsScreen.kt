package com.template.marketplace_vk.presentation.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.template.marketplace_vk.data.models.Product

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProductDetails(
    modifier: Modifier = Modifier,
    product: Product,
    onClose: () -> Unit
) {
    Scaffold(
        topBar = {
            Text(text = "close", modifier = modifier.pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Release -> {
                                onClose()
                            }

                            else -> {}
                        }
                    }
                }
            })
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            LazyRow(
                modifier = modifier
                    .height(200.dp)
            ) {
                items(product.images.size) { index ->
                    GlideImage(
                        modifier = Modifier.fillMaxSize(),
                        model = product.images[index],
                        contentDescription = null,
                        transition = CrossFade
                    )
                }
            }
        }
    }
}