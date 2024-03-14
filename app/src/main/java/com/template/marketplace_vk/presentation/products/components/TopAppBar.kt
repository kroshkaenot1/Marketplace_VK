package com.template.marketplace_vk.presentation.products.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.template.marketplace_vk.R
import com.template.marketplace_vk.presentation.products.ProductsViewModel
import com.template.marketplace_vk.presentation.utils.SearchBarStates

const val SEARCH_BAR_COLOR = 0xFFF1EDED

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    textState: MutableState<TextFieldValue>,
    searchBarState: MutableState<SearchBarStates>,
    productsViewModel: ProductsViewModel = hiltViewModel(),
    onFiltersOpen: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (searchBarState.value == SearchBarStates.SEARCHING) {
                IconButton(onClick = {
                    searchBarState.value = SearchBarStates.EMPTY
                    textState.value = TextFieldValue("")
                    productsViewModel.clearProductsList()
                    productsViewModel.fetchProducts()

                }) {
                    Icon(
                        modifier = modifier.padding(start = 10.dp, end = 10.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
            TextField(
                value = textState.value, onValueChange = { value -> textState.value = value },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (textState.value != TextFieldValue("")) {
                        IconButton(onClick = {
                            focusManager.clearFocus()
                            textState.value = TextFieldValue("")
                            if (searchBarState.value == SearchBarStates.SEARCHING) {
                                searchBarState.value = SearchBarStates.EMPTY
                                productsViewModel.clearProductsList()
                                productsViewModel.fetchProducts()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                        }
                    }
                },
                placeholder = {
                    Text(
                        text = context.getString(R.string.search_bar_placeholder),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    containerColor = Color(SEARCH_BAR_COLOR)
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        if (textState.value != TextFieldValue("")) {
                            searchBarState.value = SearchBarStates.SEARCHING
                            productsViewModel.clearProductsList()
                            productsViewModel.searchProducts(name = textState.value.text)
                        }
                    }
                )
            )
        }
        if (searchBarState.value == SearchBarStates.SEARCHING) {
            Row(
                modifier = modifier
                    .padding(10.dp)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                when (event.type) {
                                    PointerEventType.Press -> {
                                        onFiltersOpen()
                                    }

                                    else -> {}
                                }
                            }
                        }
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = context.getString(R.string.filter),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = modifier.padding(end = 10.dp))
                Icon(imageVector = Icons.Outlined.FilterList, contentDescription = null)
            }
        }
    }
}