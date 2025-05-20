package com.example.stylish.ui.screens.HomeScreens.categoriesScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.stylish.R
import com.example.stylish.data.Models.models.Productt
import com.example.stylish.presentation.widget.RatingBar


// --- Product Item Card ---
@Composable
fun ProductItemCard(
    product: Productt,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}  // Add this parameter
) {
    // Default/placeholder values for missing product information
    val placeholderImageRes = R.drawable.custom_back_arrow
    val placeholderDescription = "Product description not available"
    val placeholderPrice = "$19.99"
    val placeholderRating = 4.2f
    val placeholderReviews = "(128 reviews)"

    Card(
        modifier = modifier
            .width(180.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),  // Add clickable modifier
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        // Rest of the implementation remains the same
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                text = placeholderDescription,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = "$${product.price}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Red)
            RatingBar(product.rating)
            Text(text = "(${product.rating} stars)", fontSize = 12.sp, color = Color.Gray)

//            Row(verticalAlignment = Alignment.CenterVertically) {
//                RatingBar(placeholderRating)
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(text = placeholderReviews, fontSize = 12.sp, color = Color.Gray)
//            }
        }
    }
}



//***************


// --- Product Item Card ---
//@Composable
//fun ProductItemCard(
//    product: Product,
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit = {}  // Add this parameter
//) {
//    // Default/placeholder values for missing product information
//    val placeholderImageRes = R.drawable.custom_back_arrow
//    val placeholderDescription = "Product description not available"
//    val placeholderPrice = "$19.99"
//    val placeholderRating = 4.2f
//    val placeholderReviews = "(128 reviews)"
//
//    Card(
//        modifier = modifier
//            .width(180.dp)
//            .padding(8.dp)
//            .clickable(onClick = onClick),  // Add clickable modifier
//        elevation = CardDefaults.cardElevation(8.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        // Rest of the implementation remains the same
//        Column(modifier = Modifier.padding(8.dp)) {
//            AsyncImage(
//                model = product.thumbnail,
//                contentDescription = product.title,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .height(120.dp)
//                    .fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = product.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
//            Text(
//                text = placeholderDescription,
//                fontSize = 12.sp,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
//            )
//            Text(text = "$${product.price}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Red)
//            RatingBar(product.rating)
//            Text(text = "(${product.rating} stars)", fontSize = 12.sp, color = Color.Gray)
//
////            Row(verticalAlignment = Alignment.CenterVertically) {
////                RatingBar(placeholderRating)
////                Spacer(modifier = Modifier.width(4.dp))
////                Text(text = placeholderReviews, fontSize = 12.sp, color = Color.Gray)
////            }
//        }
//    }
//}
