package com.example.stylish.presentation.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stylish.R

@Composable
fun CategoryList() {
    val categories = listOf(
        Pair(R.drawable.beauty, "Beauty"),
        Pair(R.drawable.fashion, "Fashion"),
        Pair(R.drawable.kids, "Kids"),
        Pair(R.drawable.men, "Men"),
        Pair(R.drawable.women, "Women"),
        Pair(R.drawable.gifts, "Gifts"),
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { (imageRes, name) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(4.dp)) // Adds space between image and text
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCategoryList() {
    CategoryList()
}