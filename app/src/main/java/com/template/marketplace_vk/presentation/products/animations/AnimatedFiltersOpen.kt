package com.template.marketplace_vk.presentation.products.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable

@Composable
fun AnimatedFiltersOpen(
    isFiltersOpen: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = isFiltersOpen,
        enter = slideInVertically(animationSpec = tween(400), initialOffsetY = { it }),
        exit = slideOutVertically(animationSpec = tween(400), targetOffsetY = { it })
    ) {
        content()
    }
}
