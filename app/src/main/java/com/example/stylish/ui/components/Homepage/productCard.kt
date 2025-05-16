package com.example.stylish.presentation.widget
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import coil.compose.rememberImagePainter

@Composable fun ProductCardd(product: Productt, onFavoriteClick: (Productt) -> Unit, onProductClick: (Productt) -> Unit ) { Card( modifier = Modifier .width(180.dp) .padding(8.dp) .clickable { onProductClick(product) }, elevation = CardDefaults.cardElevation(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White) ) {
    Column(modifier = Modifier.padding(8.dp)) {
        Box {
            Image( painter = rememberImagePainter(product.thumbnail), contentDescription = product.title, modifier = Modifier .height(120.dp) .fillMaxWidth(), contentScale = ContentScale.Crop )
            IconButton( onClick = { onFavoriteClick(product) }, modifier = Modifier .align(Alignment.TopStart) .padding(4.dp) ) {
                Icon( imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = "Favorite", tint = if (product.isFavorite) Color.Red else Color.Gray ) } }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text( text = product.description, fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis )
        Text( text = "₹${product.price}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Red )
        Row(verticalAlignment = Alignment.CenterVertically)  {
            Rating(rating = product.rating.toFloat())
            Spacer(modifier = Modifier.width(4.dp))
            Text( text = "${product.stock} reviews", fontSize = 12.sp, color = Color.Gray ) } } } }

@Composable fun Rating(rating: Float) { Row { repeat(5) { index -> Icon( imageVector = Icons.Default.Star, contentDescription = "Rating", tint = if (index < rating) Color.Yellow else Color.Gray, modifier = Modifier.size(16.dp) ) } } }




@Composable
fun ProductCard(
    imageRes: Int, // Product Image
    title: String,
    description: String,
    price: String,
    rating: Float,
    reviews: String
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = description, fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = price, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RatingBar(rating)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = reviews, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}
