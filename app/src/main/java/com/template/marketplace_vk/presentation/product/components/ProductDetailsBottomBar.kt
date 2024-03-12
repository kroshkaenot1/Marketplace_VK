package com.template.marketplace_vk.presentation.product.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.template.marketplace_vk.R

@Composable
fun ProductDetailsBottomBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { },
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        ) {
            Text(text = context.getString(R.string.buy_now))
        }
        Button(
            onClick = { },
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        ) {
            Text(text = context.getString(R.string.add_to_card))
        }
    }
}