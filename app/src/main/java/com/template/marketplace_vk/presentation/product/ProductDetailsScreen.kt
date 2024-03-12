package com.template.marketplace_vk.presentation.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.template.marketplace_vk.R
import com.template.marketplace_vk.data.models.Product
import com.template.marketplace_vk.presentation.product.components.ProductDetailsBottomBar
import com.template.marketplace_vk.presentation.product.components.ProductDetailsTopBar
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProductDetails(
    modifier: Modifier = Modifier,
    product: Product,
    onClose: () -> Unit
) {
    val context = LocalContext.current
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
        LazyColumn(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            item {
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
            }
            item {
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    modifier = modifier.padding(10.dp),
                    text = product.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = modifier.padding(10.dp),
                        text = "${product.rating}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row {
                        repeat(5) { index ->
                            val icon =
                                if (index < product.rating) Icons.Filled.Star else Icons.Outlined.Star
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (index < product.rating - 0.5) Color.Red else Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(1.dp))
                        }
                    }
                }
                Row(modifier = modifier.padding(10.dp)) {
                    Column {
                        Text(
                            text = "${product.price} ₽",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = context.getString(R.string.with_discount) + " %.0f".format(
                                ceil(
                                    product.discountPercentage
                                )
                            ) + "%",
                            style = MaterialTheme.typography.titleSmall.copy(

                            )
                        )
                    }
                    Spacer(modifier = modifier.width(20.dp))
                    Column {
                        Text(
                            text = "%.0f".format(ceil(product.price * 100 / (100 - product.discountPercentage))) + "₽",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                        Text(
                            text = context.getString(R.string.without_discount),
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = Color.Gray
                            )
                        )
                    }
                    Spacer(modifier = modifier.width(40.dp))
                    Text(
                        text = if (product.stock > 0) "${product.stock} " + context.getString(R.string.quantity)
                        else context.getString(R.string.out_of_stock),
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 20.sp
                    )
                }
                Text(
                    modifier = modifier.padding(10.dp),
                    text = context.getString(R.string.description),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    modifier = modifier.padding(10.dp),
                    text = product.description
                )
                Text(
                    modifier = modifier.padding(10.dp),
                    text = context.getString(R.string.about_brand),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    modifier = modifier.padding(10.dp),
                    text = product.brand
                )
            }
        }

    }
}