package com.example.stylish.ui.components.Homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.stylish.R
//import com.google.accompanist.pager.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.example.stylish.ui.theme.Pink80


//****************
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.CircleShape
import androidx.navigation.NavController
import com.example.stylish.presentation.widget.Screen
import com.example.stylish.ui.theme.DatkPink

@Composable
fun BannerSection(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = when (page) {
                        0 -> R.drawable.girl  // Replace with your drawables
                        1 -> R.drawable.banner2
                        else -> R.drawable.banner3
                    }),
                    contentDescription = "Banner Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // This should work fine now
                )

                // 🔹 Text Overlay (Customize per banner if needed)
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "50-40% OFF",  // Example text
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = " Buy It Now  ",  // Example text
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = " All colours",  // Example text
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Button(
                        onClick = {
                            navController.navigate(Screen.SearchScreen.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Shop Now →")
                    }
                }
            }
        }
        // Dots Indicator
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3) { index ->
                val color = if (pagerState.currentPage == index) DatkPink else Color.Gray
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(
                           CircleShape,
                        )
                        .background(color)
                        .padding(4.dp)
                )
            }
        }
    }
}

