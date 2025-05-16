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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stylish.presentation.widget.ProductGridd
import com.example.stylish.presentation.widget.ProductsViewModel
import com.example.stylish.presentation.widget.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    val viewModel: ProductsViewModel = viewModel()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(searchQuery) {
        if (searchQuery.isBlank()) {
            viewModel.loadProducts(null) // load all products initially or on empty search
        } else {
            viewModel.loadProducts(searchQuery) // filtered products on search input
        }
    }

    val isLoading = viewModel.isLoading
    val error = viewModel.error

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {

            Row (){
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

//        item {
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "Search Results",
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//        }
        item {
            ProductGridd(
                viewModel = viewModel,
                category = searchQuery,
                onProductClick = { product ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("product", product)
                    navController.navigate(Screen.ProductDetail.route)
                }
            )
        }
    }
}
