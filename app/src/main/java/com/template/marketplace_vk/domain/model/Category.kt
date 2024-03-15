package com.template.marketplace_vk.domain.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Category(
    val name: String,
    val isSelected: MutableState<Boolean> = mutableStateOf(false)
)