package com.example.stylish.presentation.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stylish.presentation.widget.ProductGridd
import com.example.stylish.presentation.widget.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") } // State for search input

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Search",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it }, // Update search query state
                placeholder = { Text("Search any Product...", fontSize = 14.sp) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") }
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Search Results",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        item {
            // You may want to pass the searchQuery to the ProductGridd to filter products
            ProductGridd(
                category = searchQuery, // Use searchQuery to filter products
                onProductClick = { product ->
                    // Passing the product to the details screen
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("product", product)
                    navController.navigate(Screen.ProductDetail.route)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchScreen() {
    SearchScreen(navController = rememberNavController()) // Preview with a mock NavController
}
