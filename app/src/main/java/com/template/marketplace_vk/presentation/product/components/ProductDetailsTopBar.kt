package com.template.marketplace_vk.presentation.product.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun ProductDetailsTopBar(
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    Row(modifier = modifier.padding(15.dp)) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = modifier
                .pointerInput(Unit) {
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
                }
        )
        Spacer(modifier = modifier.weight(1f))
        Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null)
        Spacer(modifier = modifier.width(25.dp))
        Icon(imageVector = Icons.Outlined.Share, contentDescription = null)
        Spacer(modifier = modifier.width(10.dp))
    }
}