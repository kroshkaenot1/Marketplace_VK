package com.template.marketplace_vk.presentation.products.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.template.marketplace_vk.data.models.Product

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onProductClick: (Product) -> Unit
) {
    Column(modifier = modifier
        .padding(10.dp)
        .pointerInput(Unit) {
            var startPos = androidx.compose.ui.geometry.Offset.Zero
            var movedFarEnough = false
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    when (event.type) {
                        PointerEventType.Press -> {
                            startPos = event.changes.first().position
                            movedFarEnough = false
                        }

                        PointerEventType.Move -> {
                            if (!movedFarEnough) {
                                val currentPosition = event.changes.last().position
                                val distanceX = kotlin.math.abs(currentPosition.x - startPos.x)
                                val distanceY = kotlin.math.abs(currentPosition.y - startPos.y)
                                movedFarEnough = distanceX.coerceAtLeast(distanceY) > 20.dp.toPx()
                            }
                        }

                        PointerEventType.Release -> {
                            if (event.changes.first().position == event.changes.last().position &&
                                !movedFarEnough
                            ) {
                                onProductClick(product)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }) {
        Box(modifier = modifier) {
            GlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(10.dp)),
                model = product.thumbnail,
                contentDescription = null,
                transition = CrossFade
            )
            Box(
                modifier = modifier
                    .size(28.dp)
                    .align(Alignment.TopStart)
                    .background(color = Color.Red, shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "%.0f".format(product.discountPercentage) + "%",
                    style = TextStyle.Default.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    ),
                    color = Color.White
                )
            }
        }
        Spacer(modifier = modifier.height(8.dp))
        Column() {
            Text(
                text = "${product.price} ₽",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = modifier.height(4.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = modifier.height(4.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}