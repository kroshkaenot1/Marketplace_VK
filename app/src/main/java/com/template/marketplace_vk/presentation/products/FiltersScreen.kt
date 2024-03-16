package com.template.marketplace_vk.presentation.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.template.marketplace_vk.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Filters(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onCloseAndConfirm: () -> Unit,
    productsViewModel: ProductsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val listOfCategories = productsViewModel.listOfCategories.collectAsState()
    Scaffold(topBar = {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = modifier.align(Alignment.Center),
                text = context.getString(R.string.filter),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            IconButton(modifier = modifier.align(Alignment.CenterEnd), onClick = {
                onClose()
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    }, bottomBar = {
        Box(modifier = modifier.fillMaxWidth()) {
            Button(
                modifier = modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Center), onClick = onCloseAndConfirm
            ) {
                Text(text = context.getString(R.string.show_products))
            }
        }
    }) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = context.getString(R.string.filter_categories),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = modifier.height(10.dp))
            FlowRow(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOfCategories.value.forEach { category ->
                    Row(
                        modifier = modifier
                            .clickable { category.isSelected.value = !category.isSelected.value }
                            .background(
                                color = if (category.isSelected.value) Color(0xFF383636) else Color(
                                    0xFFF1EDED
                                ),
                                shape = RoundedCornerShape(20.dp)
                            ),
                        verticalAlignment = CenterVertically) {
                        Text(
                            modifier = modifier
                                .padding(start = 8.dp, top = 10.dp, bottom = 10.dp, end = 4.dp),
                            text = category.name,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = if (category.isSelected.value) Color.White else Color.Black
                            )
                        )
                        if (category.isSelected.value) {
                            Icon(
                                modifier = modifier.padding(end = 5.dp),
                                imageVector = Icons.Default.Clear,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}