package com.example.stylish.ui.screens.BNBscreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.stylish.data.Models.models.ProductsViewModelFactory
import com.example.stylish.data.local.FavoriteDataStore
import com.example.stylish.presentation.widget.ProductCardd
import com.example.stylish.presentation.widget.ProductsViewModel
import com.example.stylish.presentation.widget.Screen

@Composable
fun WishlistPage(navController: NavController) {
    val context = LocalContext.current
    val favoriteDataStore = remember { FavoriteDataStore(context) }

    val viewModel: ProductsViewModel = viewModel(
        factory = ProductsViewModelFactory(favoriteDataStore)
    )
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
            // ‑‑‑‑‑ Grid of favorites
            // inside FavoritesScreen (or wherever the grid is)

            LazyVerticalGrid(GridCells.Fixed(2)) {
                items(viewModel.favorites) { product ->
                    ProductCardd(
                        product = product,
                        onFavoriteClick = { viewModel.toggleFavorite(it) },

                        // UPDATED ↓↓↓
                        onProductClick = { selected ->
                            // put whole object into the SavedStateHandle, then navigate
                            navController.currentBackStackEntry  // ← NEW
                                ?.savedStateHandle
                                ?.set("product", selected)       // ← NEW
                            navController.navigate(Screen.ProductDetail.createRoute(product.id)) // ← NEW
                        }
                    )
                }
            }


//            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
//                items(viewModel.favorites.size) { index ->
//                    ProductCardd(
//                        product = viewModel.favorites[index],
//                        onFavoriteClick = { viewModel.toggleFavorite(it) },
//                        onProductClick = { product ->
//                            // Handle product click, e.g., navigate to product detail screen
//                        }
//                    )
//                }
//            }
        }
    }
}