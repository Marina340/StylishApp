package com.example.stylish.presentation.pages
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import com.example.stylish.R

@Composable
fun ShoppingBagScreen() {
    val pinkColor = Color(0xFFE91E63)
    val lightGray = Color(0xFFF5F5F5)

    var expandedSize by remember { mutableStateOf(false) }
    var selectedSize by remember { mutableStateOf("42") }

    var expandedQty by remember { mutableStateOf(false) }
    var selectedQty by remember { mutableStateOf("1") }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(lightGray)
                    .drawBehind {
                        // Draw only the top border line
                        drawLine(
                            color = Color(0xFFCACACA),
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 2.dp.toPx()
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("₹ 7,000.00", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(
                                text = "View Details",
                                color = pinkColor,
                                fontSize = 12.sp,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable { }
                            )
                        }
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = pinkColor),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.height(48.dp)
                        ) {
                            Text("Proceed to Payment")
                        }
                    }
                }
            }
        }


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Top Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.custom_back_arrow),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { }
                )
                Text(
                    "Shopping Bag",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorites",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { }
                )
            }

            // Product Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.product_image),
                    contentDescription = "Product",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(20.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("Women's Casual Wear", fontWeight = FontWeight.Bold)
                    Text(
                        "Checked Single-Breasted Blazer",

                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DropdownBox(
                            label = "Size $selectedSize",
                            expanded = expandedSize,
                            onClick = { expandedSize = true },
                            items = listOf("38", "40", "42", "44"),
                            onItemClick = {
                                selectedSize = it
                                expandedSize = false
                            }
                        )
                        DropdownBox(
                            label = "Qty $selectedQty",
                            expanded = expandedQty,
                            onClick = { expandedQty = true },
                            items = listOf("1", "2", "3", "4", "5"),
                            onItemClick = {
                                selectedQty = it
                                expandedQty = false
                            }
                        )
                    }

                    Row {
                        Text("Delivery by ", fontSize = 12.sp)
                        Text("10 May 2XXX", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            // Apply Coupon Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_coupon), // Ensure this exists
                        contentDescription = "Coupon",
                        tint = Color.Black,
                        modifier = Modifier.size(35.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Apply Coupons", fontSize = 18.sp)
                }
                Text(
                    text = "Select",
                    color = pinkColor,
                    modifier = Modifier.clickable { },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(modifier = Modifier.padding(vertical = 19.dp), color = lightGray,thickness = 3.dp)

            Text("Order Payment Details", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Box(modifier = Modifier.padding(top = 15.dp)) {
                OrderDetailRow("Order Amounts", "₹ 7,000.00")
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Convenience ")
                    Text(
                        text = "Know More",
                        color = pinkColor,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable { }
                    )
                }

                Spacer(modifier = Modifier.weight(1f)) // Pushes "Apply Coupon" to the end

                Text(
                    text = "Apply Coupon",
                    color = pinkColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { }
                )
            }


            OrderDetailRow("Delivery Fee", "Free",valueColor = pinkColor)

            Divider(modifier = Modifier.padding(vertical = 19.dp), color = lightGray,thickness = 3.dp)
            OrderDetailRow("Order Total", "₹ 7,000.00", isBold = true)

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("EMI Available ")
                Text(
                    text = "Details",
                    color = pinkColor,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable { }
                )
            }
        }
    }
}

@Composable
fun OrderDetailRow(label: String, value: String, isBold: Boolean = false, valueColor: Color = Color.Unspecified) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = value,
            color = valueColor,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun DropdownBox(
    label: String,
    expanded: Boolean,
    onClick: () -> Unit,
    items: List<String>,
    onItemClick: (String) -> Unit
) {
    Box(modifier = Modifier.height(36.dp)) {
        Row(
            modifier = Modifier
                .border(1.dp, Color.Gray)
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, fontSize = 12.sp)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Gray)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { onItemClick(items[0]) }) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

