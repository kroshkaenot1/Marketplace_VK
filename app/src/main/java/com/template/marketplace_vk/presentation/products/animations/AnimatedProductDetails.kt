package com.template.marketplace_vk.presentation.products.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import com.template.marketplace_vk.data.models.Product

@Composable
fun AnimatedProductDetails(
    product: Product?,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = product != null,
        enter = slideInHorizontally(animationSpec = tween(400), initialOffsetX = { it }),
        exit = slideOutHorizontally(animationSpec = tween(400), targetOffsetX = { it })
    ) {
        product?.let {
            content()
        }
    }
}
