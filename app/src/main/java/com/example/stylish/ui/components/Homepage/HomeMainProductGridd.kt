package com.example.stylish.presentation.widget
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.stylish.data.Models.models.Productt

@Composable
fun ProductGridd(viewModel: ProductsViewModel = viewModel(), category: String? = null, onProductClick: (Productt) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().heightIn(min = 200.dp, max = 600.dp)) {
        viewModel.loadProducts(category) // Load products based on category

        when {
            viewModel.isLoading -> {
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
            viewModel.error != null -> {
                Text(
                    text = "Error: ${viewModel.error}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(viewModel.products.size) { index ->
                        ProductCardd(
                            product = viewModel.products[index],
                            onFavoriteClick = { viewModel.toggleFavorite(it) },
                            onProductClick = onProductClick
                        )
                    }
                }
            }
        }
    }
}
