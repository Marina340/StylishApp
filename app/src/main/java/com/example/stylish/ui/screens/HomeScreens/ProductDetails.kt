package com.example.stylish.ui.screens.HomeScreens

import CartButtons
import GrayActionButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.example.stylish.presentation.widget.ProductHorizontalList
import com.example.stylish.presentation.widget.Screen
import com.example.stylish.data.Models.models.Productt
import com.example.stylish.data.local.FavoriteDataStore
import com.example.stylish.presentation.widget.ProductsViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    productId: Int,
    navController: NavController,
    favoriteDataStore: FavoriteDataStore,
    viewModel: ProductsViewModel
) {
    val productState = remember(productId) {
        mutableStateOf(viewModel.getProductById(productId))
    }

    val product = productState.value

    if (product == null) {
        Text("Product not found")
        return
    }

    val coroutineScope = rememberCoroutineScope()

    var selectedSize by remember { mutableStateOf("7 UK") }
    var showFullDescription by remember { mutableStateOf(false) }
    var showStoreDialog by remember { mutableStateOf(false) }
    var showVipDialog by remember { mutableStateOf(false) }
    var showReturnDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Product Image Box with Back and Favorite buttons
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberImagePainter(product.thumbnail),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.toggleFavorite(product) // ✅ pass the entire object
                        productState.value = viewModel.getProductById(productId)
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (product.isFavorite) Color.Red else Color.Black
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = product.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.description,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "₹${product.price * 2}",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp),
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = "₹${product.price}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE53935)
                )
                Text(
                    text = "50% Off",
                    fontSize = 14.sp,
                    color = Color(0xFFE53935),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        // Delivery Info Box
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .background(Color(0xFFFFC0CB))
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .clickable { /* Handle delivery info click */ }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Delivery in",
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                        Text(
                            text = "1 Hour",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        // Action buttons row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GrayActionButton(
                text = "Nearest Store",
                img = "v3",
                onClick = { showStoreDialog = true }
            )
            GrayActionButton(
                text = "VIP",
                img = "v2",
                onClick = { showVipDialog = true }
            )
            GrayActionButton(
                text = "Return Policy",
                img = "v1",
                onClick = { showReturnDialog = true }
            )
        }

        // Cart buttons and similar products
        CartButtons(navController, product)

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Similar To",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "50+ Items",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            ProductHorizontalList(
                products = viewModel.products,
                isLoading = viewModel.isLoading,
                onToggleFavorite = { clickedProduct ->
                    viewModel.toggleFavorite(clickedProduct)
                },
                onProductClick = { clickedProduct ->
                    navController.navigate("productDetail/${clickedProduct.id}")
                }
            )
        }
    }

    // Dialogs
    if (showStoreDialog) {
        AlertDialog(
            onDismissRequest = { showStoreDialog = false },
            title = { Text("Nearest Store") },
            text = { Text("Our nearest store is at 123 Beauty Street, open 9AM-9PM daily") },
            confirmButton = {
                TextButton(onClick = { showStoreDialog = false }) { Text("OK") }
            }
        )
    }
    if (showVipDialog) {
        AlertDialog(
            onDismissRequest = { showVipDialog = false },
            title = { Text("VIP Benefits") },
            text = { Text("Join our VIP program for exclusive discounts and early access to new products!") },
            confirmButton = {
                TextButton(onClick = { showVipDialog = false }) { Text("OK") }
            }
        )
    }
    if (showReturnDialog) {
        AlertDialog(
            onDismissRequest = { showReturnDialog = false },
            title = { Text("Return Policy") },
            text = { Text("There are some policies you should know before starting the return process for the current product.") },
            confirmButton = {
                TextButton(onClick = { showReturnDialog = false }) { Text("OK") }
            }
        )
    }
}

