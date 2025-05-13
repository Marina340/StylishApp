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


//@Composable
//fun ProductGrid() {
//    val products = listOf(
//        Product(R.drawable.ps4, "Sony PS4", "Sony PS4 Console, 1TB Slim with 3 Games", "₹1,999", 4.5f, "8,35,566"),
//        Product(R.drawable.realme7, "Realme 7", "6GB RAM | 64GB ROM | Expandable Up to 256GB", "₹3,499", 4.2f, "3,44,567"),
//        Product(R.drawable.ps4, "Gaming Laptop", "16GB RAM | RTX 3060 | 1TB SSD", "₹8,999", 4.8f, "5,12,345"),
//        Product(R.drawable.realme7, "Wireless Headphones", "Noise Cancelling | 40hr Battery", "₹1,299", 4.6f, "2,12,789"),
//        Product(R.drawable.ps4, "DSLR Camera", "4K Video | 24MP | Wi-Fi", "₹5,999", 4.7f, "1,89,654"),        Product(R.drawable.realme7, "Wireless Headphones", "Noise Cancelling | 40hr Battery", "₹1,299", 4.6f, "2,12,789"),
//        Product(R.drawable.ps4, "DSLR Camera", "4K Video | 24MP | Wi-Fi", "₹5,999", 4.7f, "1,89,654"),        Product(R.drawable.realme7, "Wireless Headphones", "Noise Cancelling | 40hr Battery", "₹1,299", 4.6f, "2,12,789"),
//        Product(R.drawable.ps4, "DSLR Camera", "4K Video | 24MP | Wi-Fi", "₹5,999", 4.7f, "1,89,654"),        Product(R.drawable.realme7, "Wireless Headphones", "Noise Cancelling | 40hr Battery", "₹1,299", 4.6f, "2,12,789"),
//        Product(R.drawable.ps4, "DSLR Camera", "4K Video | 24MP | Wi-Fi", "₹5,999", 4.7f, "1,89,654"),
//    )
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .heightIn(min = 200.dp, max = 600.dp) // ✅ Allows grid to scroll inside LazyColumn
//    ) {
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            modifier = Modifier.fillMaxSize(),
//        ) {
//            items(products.size) { index ->
//                ProductCard(
//                    imageRes = products[index].imageRes,
//                    title = products[index].title,
//                    description = products[index].description,
//                    price = products[index].price,
//                    rating = products[index].rating,
//                    reviews = products[index].reviews
//                )
//            }
//        }
//    }
//}
