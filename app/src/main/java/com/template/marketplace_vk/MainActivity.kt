package com.template.marketplace_vk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.template.marketplace_vk.presentation.products.Products
import com.template.marketplace_vk.ui.theme.Marketplace_VKTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Marketplace_VKTheme {
                Products()
            }
        }
    }
}