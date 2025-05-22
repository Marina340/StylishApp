package com.example.stylish.ui.components.PaymentCardComponent

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import com.example.stylish.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.stylish.data.Models.models.ShoppinglistItemModel

@SuppressLint("ResourceAsColor")
@Composable
fun ShoppingListItemComponent(shoppinglistItemModel: ShoppinglistItemModel) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image section
                Image(
                    painter = rememberImagePainter(shoppinglistItemModel.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = shoppinglistItemModel.itemName,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                    Text(
                        text = "Quantity : ",
                        color = Color.Black
                    )
                        Text(
                            text = shoppinglistItemModel.quantity.toString(),
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = shoppinglistItemModel.itemRate.toString(),
                            color = Color.Black
                        )

                      Spacer(modifier = Modifier.size(4.dp))

                        val fullStars = shoppinglistItemModel.itemRate.toInt()
                        val hasHalfStar = (shoppinglistItemModel.itemRate % 1) >= 0.5
                        val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0

                        repeat(fullStars) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Star",
                                tint = Color(0xFFFFD700), // gold
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        if (hasHalfStar) {
                            Icon(
                                imageVector = Icons.Filled.StarHalf,
                                contentDescription = "Half Star",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        repeat(emptyStars) {
                            Icon(
                                imageVector = Icons.Filled.StarBorder,
                                contentDescription = "Empty Star",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                    ) {
                        Text(
                            text = "$ ${shoppinglistItemModel.itemPrice}",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(8.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(R.color.ColorNeutral60))
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Order (1):",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$ ${shoppinglistItemModel.itemPrice*shoppinglistItemModel.quantity}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}
