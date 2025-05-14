package com.example.stylish.ui.screens

import CartButtons
import GrayActionButton
import RedActionButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.stylish.presentation.widget.Productt
import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlinx.parcelize.Parcelize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.stylish.presentation.widget.ProductHorizontalList
import com.example.stylish.presentation.widget.ProductsViewModel
import com.example.stylish.presentation.widget.Screen

@Composable
fun ProductDetailScreen(product: Productt,navController: NavController) {
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
        // Product Image
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberImagePainter(product.thumbnail),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            // Favorite button
            IconButton(
                onClick = { /* Handle favorite */ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (product.isFavorite) Color.Red else Color.White
                )
            }
        }

//        // Size Selector
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text("Size: $selectedSize", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                listOf("6 UK", "7 UK", "8 UK", "9 UK", "10 UK").forEach { size ->
//                    FilterChip(
//                        selected = size == selectedSize,
//                        onClick = { selectedSize = size },
//                        label = { Text(size) },
//                        modifier = Modifier.padding(vertical = 4.dp)
//                    )
//                }
//            }
//        }

        // Product Title and Price
        // Product Title, Description and Price
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = product.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product Description
            Text(
                text = product.description,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Price Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "₹${product.price * 2}", // Original price
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp),
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = "₹${product.price}", // Discounted price
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
        // Delivery Info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Box(
                modifier = Modifier
                    .height(60.dp)  // Reduced from 65.dp
                    .background(Color(0xFFFFC0CB))
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .clickable { /* Handle delivery info */ }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Delivery in",
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                        Text(
                            text = "1 Hour",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,  // Slightly reduced from 25.sp
                            color = Color.Black
                        )
                    }
                    // Additional text/content on the right side
//                    Text(
//                        text = "Free delivery",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 14.sp,
//                        color = Color.Black,
//                        modifier = Modifier.padding(end = 8.dp)
//                    )
//                    Icon(
//                        imageVector = Icons.Default.ArrowForward,
//                        contentDescription = "More info",
//                        tint = Color.Black
//                    )
                }
            }

            // Optional: Add additional content below the box
//            Text(
//                text = "Order now and get it by 5:30 PM",
//                fontSize = 12.sp,
//                modifier = Modifier.padding(top = 4.dp, start = 8.dp)
//            )
        }
        // Store Info and Actions
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

// Action Buttons
        CartButtons( navController)

        // Similar Products Section
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

            // Horizontal list of similar products
            ProductHorizontalList(onProductClick = { product ->
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("product", product)
                navController.navigate(Screen.ProductDetail.route)
            })


        }
    }

    // Alert Dialogs (should be outside the main Column)
    if (showStoreDialog) {
        AlertDialog(
            onDismissRequest = { showStoreDialog = false },
            title = { Text("Nearest Store") },
            text = { Text("Our nearest store is at 123 Beauty Street, open 9AM-9PM daily") },
            confirmButton = {
                TextButton(onClick = { showStoreDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    if (showVipDialog) {
        AlertDialog(
            onDismissRequest = { showVipDialog = false },
            title = { Text("VIP Benefits") },
            text = { Text("Join our VIP program for exclusive discounts and early access to new products!") },
            confirmButton = {
                TextButton(onClick = { showVipDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    if (showReturnDialog) {
        AlertDialog(
            onDismissRequest = { showReturnDialog = false },
            title = { Text("Return Policy") },
            text = { Text("there is some policies should you know before startring returning process for the current product ") },//product.returnPolicy
            confirmButton = {
                TextButton(onClick = { showReturnDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}




