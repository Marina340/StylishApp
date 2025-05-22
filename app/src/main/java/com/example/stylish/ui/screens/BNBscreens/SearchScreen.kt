package com.example.stylish.presentation.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.stylish.data.Models.models.Productt
import com.example.stylish.presentation.widget.ProductGridd
import com.example.stylish.presentation.widget.Screen
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stylish.domain.api.ProductsViewModel
import com.example.stylish.presentation.widget.ProductGridd
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: ProductsViewModel ) {
    var searchQuery by remember { mutableStateOf("") }
    var lastQuery by remember { mutableStateOf("") }

    // Debounce logic to avoid calling API on every keystroke instantly
    LaunchedEffect(searchQuery) {
        delay(400)
        if (searchQuery != lastQuery) {
            if (searchQuery.isBlank()) {
                viewModel.loadProducts()
            } else {
                viewModel.searchProducts(searchQuery)
            }
            lastQuery = searchQuery
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Spacer(Modifier.width(15.dp))
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search any Product...", fontSize = 14.sp) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") }
            )
        }

        item {
            ProductGridd(
                products = viewModel.products,
                isLoading = viewModel.isLoading,
                onToggleFavorite = { viewModel.toggleFavorite(it) },
                onProductClick = { product ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("product", product)
                    navController.navigate(Screen.ProductDetail.createRoute(product.id))
                }
            )
        }
    }
}
