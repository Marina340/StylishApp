package com.example.stylish.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stylish.presentation.widget.ProductCardd
import com.example.stylish.presentation.widget.ProductsViewModel

@Composable
fun WishlistPage(viewModel: ProductsViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Wishlist",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        if (viewModel.favorites.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No favorite products yet.")
            }
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(viewModel.favorites.size) { index ->
                    ProductCardd(
                        product = viewModel.favorites[index],
                        onFavoriteClick = { viewModel.toggleFavorite(it) },
                        onProductClick = { product ->
                            // Handle product click, e.g., navigate to product detail screen
                        }
                    )
                }
            }
        }
    }
}
