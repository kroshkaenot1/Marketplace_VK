package com.template.marketplace_vk.presentation.products.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.template.marketplace_vk.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Filters(
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    Scaffold(topBar = {
        Box(modifier = modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = modifier.align(Alignment.Center),
                text = context.getString(R.string.filter),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            IconButton(modifier = modifier.align(Alignment.CenterEnd), onClick = { onClose() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    }, bottomBar = {
        Button(modifier = modifier.padding(bottom = 10.dp), onClick = onClose) {
            Text(text = context.getString(R.string.show_products))
        }
    }) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            item {

            }
        }
    }
}