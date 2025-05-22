package com.example.stylish.presentation.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stylish.data.Models.models.Productt

@Composable
fun ProductGridd(
    products: List<Productt>,
    isLoading: Boolean,
    onToggleFavorite: (Productt) -> Unit,
    onProductClick: (Productt) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp, max = 600.dp)
    ) {
        when {
            isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading products...")
                }
            }
            products.isEmpty() -> {
                Text(
                    text = "No products available.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(
                        items = products,
                        key = { it.id }
                    ) { product ->
                        ProductCardd(
                            product = product,
                            onFavoriteClick = { onToggleFavorite(product) },
                            onProductClick = onProductClick
                        )
                    }
                }
            }
        }
    }
}
