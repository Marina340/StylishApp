package com.example.stylish.ui.screens.HomeScreens.categoriesScreens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stylish.data.Models.models.Productt
import com.example.stylish.domain.api.api
import com.example.stylish.presentation.widget.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// --- Product List Screen ---
@Composable
fun ProductListScreen(category: String, onProductClick: (Productt) -> Unit, navController : NavController) {
    var products by remember { mutableStateOf<List<Productt>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(category) {
        try {
            products = withContext(Dispatchers.IO) {
                api.getProductsByCategory(category).products
            }

        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            Text(
                text = error ?: "Failed to load products",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
        ///***************************************** if it works :
        else -> {
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                Row(){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Spacer(Modifier.width(15.dp))
                    Text("$category", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(8.dp))
//the main part:
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(products) { product ->
                        ProductItemCard(product = product) {
                            // ⬇️ Save this product for the next destination
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("product", product)

                            // ⬇️ Navigate to the detail screen route you already declared
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("product", product)

                            navController.navigate(Screen.ProductDetail.createRoute(product.id))

                        }
                    }

//                    items(products) { product ->
//                        ProductItemCard(product = product) {
//                            onProductClick(product)
//                        }
//                    }
                }

            }
        }
    }
}

//**************************

// --- Product List Screen ---
//@Composable
//fun ProductListScreen(category: String, onProductClick: (Product) -> Unit , navController : NavController) {
//    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
//    var isLoading by remember { mutableStateOf(true) }
//    var error by remember { mutableStateOf<String?>(null) }
//
//    LaunchedEffect(category) {
//        try {
//            products = withContext(Dispatchers.IO) {
//                api.getProductsByCategory(category).products
//            }
//
//        } catch (e: Exception) {
//            error = e.message
//        } finally {
//            isLoading = false
//        }
//    }
//
//    when {
//        isLoading -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        }
//        error != null -> {
//            Text(
//                text = error ?: "Failed to load products",
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//        else -> {
//            Column(Modifier.fillMaxSize().padding(16.dp)) {
//                Row(){
//                    Icon(
//                        imageVector = Icons.Filled.ArrowBack,
//                        contentDescription = "Back",
//                        modifier = Modifier
//                            .size(25.dp)
//                            .clickable {
//                                navController.popBackStack()
//                            }
//                    )
//                    Spacer(Modifier.width(15.dp))
//                    Text("$category", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
//                }
//                Spacer(Modifier.height(8.dp))
//
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(2),
//                    verticalArrangement = Arrangement.spacedBy(12.dp),
//                    horizontalArrangement = Arrangement.spacedBy(12.dp),
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    items(products) { product ->
//                        ProductItemCard(product = product) {
//                            onProductClick(product)
//                        }
//                    }
//                }
//
//            }
//        }
//    }
//}
//
