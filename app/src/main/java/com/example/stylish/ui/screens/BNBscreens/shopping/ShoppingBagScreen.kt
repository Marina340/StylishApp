package com.example.stylish.presentation.pages

import CartManager
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.stylish.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingBagScreen(navController: NavController) {
    val context = LocalContext.current
    val cartManager = remember { CartManager(context) }
    val cartItems by cartManager.cartItems.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val excludedCategories = listOf(
        "womens-bags", "womens-jewellery", "womens-watches",
        "mens-watches", "skin-care", "beauty"
    )

    val pinkColor = Color(0xFFE91E63)
    val lightGray = Color(0xFFF5F5F5)

    val selectedSizeMap = remember { mutableStateMapOf<Int, String>() }
    val selectedQtyMap = remember { mutableStateMapOf<Int, String>() }
    val expandedSizeMap = remember { mutableStateMapOf<Int, Boolean>() }
    val expandedQtyMap = remember { mutableStateMapOf<Int, Boolean>() }

    val totalAmount by remember(cartItems, selectedQtyMap) {
        derivedStateOf {
            cartItems.sumOf { item ->
                val qty = selectedQtyMap[item.id]?.toDoubleOrNull() ?: 1.0
                (item.price * qty).toDouble()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(lightGray)
                    .drawBehind {
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
                            Text(
                                "₹ ${totalAmount}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "View Details",
                                color = pinkColor,
                                fontSize = 12.sp,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable { }
                            )
                        }
                        Button(
                            onClick = {
                                if (cartItems.isEmpty()) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("No items in the cart!")

                                    }
                                } else {
                                    scope.launch {
                                        navController.navigate("checkout")
                                    }
                                }
                            },
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
                        .clickable { navController.navigateUp() }
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
                        .clickable { /* Handle favorites */ }
                )
            }

            // Dynamic Product Section (loop over cartItems)
            cartItems.forEach { item ->
                // Initialize selectedSize and Qty if not set
                if (!selectedSizeMap.containsKey(item.id)) selectedSizeMap[(item.id)] = "42"
                if (!selectedQtyMap.containsKey(item.id)) selectedQtyMap[item.id] = item.quantity.toString()
                if (!expandedSizeMap.containsKey(item.id)) expandedSizeMap[(item.id)] = false
                if (!expandedQtyMap.containsKey(item.id)) expandedQtyMap[(item.id)] = false

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(item.thumbnail),
                        contentDescription = item.description,
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
                        Text(item.category ?: "Category", fontWeight = FontWeight.Bold)
                        Text(
                            item.title ?: "Product Name",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (item.category != null && !excludedCategories.contains(item.category)) {
                                DropdownBox(
                                    label = "Size ${selectedSizeMap[item.id]}",
                                    expanded = expandedSizeMap[item.id] ?: false,
                                    onClick = { expandedSizeMap[item.id] = true },
                                    items = listOf("38", "40", "42", "44"),
                                    onItemClick = {
                                        selectedSizeMap[item.id] = it
                                        expandedSizeMap[item.id] = false
                                    },
                                    onDismiss = {
                                        expandedSizeMap[item.id] = false
                                    }
                                )
                            }
                            DropdownBox(
                                label = "Qty ${selectedQtyMap[item.id]}",
                                expanded = expandedQtyMap[item.id] ?: false,
                                onClick = { expandedQtyMap[item.id] = true },
                                items = listOf("1", "2", "3", "4", "5"),
                                onItemClick = {
                                    selectedQtyMap[item.id] = it
                                    expandedQtyMap[item.id] = false
                                    val updatedProduct = item.copy(quantity = it.toInt())
                                    CoroutineScope(Dispatchers.IO).launch {
                                        cartManager.updateCartItem(updatedProduct)
                                    }
                                },
                                onDismiss = {
                                    expandedQtyMap[item.id] = false
                                }
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    cartManager.removeFromCart(item)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove Item",
                                tint = Color.Red
                            )
                        }
                        Row {
                            Text("Delivery by ", fontSize = 12.sp)
                            Text("10 May 2XXX", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
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
                        painter = painterResource(id = R.drawable.ic_coupon),
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
                    modifier = Modifier.clickable { /* Coupon selection */ },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(modifier = Modifier.padding(vertical = 19.dp), color = lightGray, thickness = 3.dp)

            Text("Order Payment Details", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Box(modifier = Modifier.padding(top = 15.dp)) {
                OrderDetailRow("Order Amounts", "₹ $totalAmount")
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
                            .clickable { /* Show more info */ }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Apply Coupon",
                    color = pinkColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* Apply coupon action */ }
                )
            }

            OrderDetailRow("Delivery Fee", "Free", valueColor = pinkColor)

            Divider(modifier = Modifier.padding(vertical = 19.dp), color = lightGray, thickness = 3.dp)
            OrderDetailRow("Order Total", "₹ $totalAmount.00", isBold = true)

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
                        .clickable { /* EMI details */ }
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
    onItemClick: (String) -> Unit,
    onDismiss: () -> Unit
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
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemClick(item)
                        onDismiss()
                    }
                )
            }
        }
    }
}
