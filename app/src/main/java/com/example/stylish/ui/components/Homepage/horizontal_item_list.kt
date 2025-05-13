package com.example.stylish.presentation.widget
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stylish.R
import com.example.stylish.data.Models.Product
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
//

@Composable
fun ProductHorizontalList(
    viewModel: ProductsViewModel = viewModel(),
    onProductClick: (Productt) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp, max = 250.dp)
    ) {
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
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    modifier = Modifier.fillMaxSize(),
                    state = rememberLazyGridState(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(viewModel.products) { product ->
                        ProductCardd(
                            product = product,
                            onFavoriteClick = { viewModel.toggleFavorite(it) },
                            onProductClick = onProductClick,
                            // modifier = Modifier.width(160.dp)
                        )
                    }
                }
            }
        }
    }
}
