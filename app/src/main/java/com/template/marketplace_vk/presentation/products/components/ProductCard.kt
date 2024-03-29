package com.template.marketplace_vk.presentation.products.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.template.marketplace_vk.data.models.Product
import com.template.marketplace_vk.presentation.utils.shimmerEffect
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onProductClick: (Product) -> Unit
) {
    Column(modifier = modifier
        .padding(10.dp)
        .pointerInput(Unit) {
            var startPos = Offset.Zero
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
        ImageOfProduct(product = product)
        Spacer(modifier = modifier.height(8.dp))
        Column {
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageOfProduct(modifier: Modifier = Modifier,
                   product: Product){
    Box(modifier = modifier) {
        GlideSubcomposition(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            model = product.thumbnail
        ) {
            when (state) {
                RequestState.Failure -> {}
                RequestState.Loading -> {
                    Box(
                        modifier = modifier
                            .size(200.dp)
                            .shimmerEffect()
                            .clip(shape = RoundedCornerShape(10.dp))
                    )
                }

                is RequestState.Success -> Image(painter = painter, contentDescription = null)
            }
        }
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
}