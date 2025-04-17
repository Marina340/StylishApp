package com.example.stylish.ui.screens

import android.R.attr.onClick
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stylish.R
import com.example.stylish.data.Models.ShoppinglistItemModel
import com.example.stylish.ui.components.ButtonComponent
import com.example.stylish.ui.components.PaymentCardComponent
import com.example.stylish.ui.components.PaymentSuccessDialog
import com.example.stylish.ui.components.ShoppingListItemComponent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceAsColor")
@Composable
fun Checkout(modifier: Modifier = Modifier) {
    val shoppingList = listOf(
        ShoppinglistItemModel(R.drawable.item_image, "Women’s Casual Wear", listOf("black", "red"), 4.0, 36.6),
        ShoppinglistItemModel(R.drawable.item_image, "Men’s Sport Jacket", listOf("blue", "green"), 4.5, 59.9),
        ShoppinglistItemModel(R.drawable.item_image, "Kid’s Hoodie", listOf("yellow", "pink"), 4.2, 25.3)
    )

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                        Text(
                            text = "Checkout",
                            fontWeight = FontWeight.Bold,
                        )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back action */ }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIos,
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .fillMaxSize()
            ) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "address location pin outline",
                            tint = Color.Black,
                            modifier = Modifier.size(26.dp)
                        )
                        Text(
                            text = "Delivery Address",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier
                                .weight(0.7f)
                                .fillMaxHeight()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Address :", color = Color.Black)
                                    Icon(
                                        painter = painterResource(id = R.drawable.edit_square_icon),
                                        contentDescription = "Edit icon",
                                        tint = Color.Black,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                                Spacer(Modifier.height(5.dp))
                                Text(
                                    text = "216 St Paul's Rd, London N1 2LL, UK\nContact :  +44-784232",
                                    color = Color.Black
                                )
                            }
                        }
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier
                                .weight(0.3f)
                                .fillMaxHeight()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.add_circle_icon),
                                    contentDescription = "Add icon",
                                    tint = Color.Black,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                    }
                }

                item {
                    Row {
                        Text(
                            text = "Shopping List",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                items(shoppingList.size) { index ->
                    ShoppingListItemComponent(shoppingList[index])
                }

                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Order",
                                color = Color(0xFF9E9E9E)
                            )
                            Text(
                                text = "$7000",
                                color = Color(0xFF9E9E9E)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Shipping",
                                color = Color(0xFF9E9E9E)
                            )
                            Text(
                                text = "$70",
                                color = Color(0xFF9E9E9E)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total",
                                color = Color.Black
                            )
                            Text(
                                text = "$7070",
                                color = Color.Black
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(R.color.ColorNeutral60))
                        )
                        Spacer(modifier= Modifier.height(10.dp))
                        Text(
                            text = "Payment",
                            color = Color.Black,
                        )
                        Spacer(modifier= Modifier.height(10.dp))
                        PaymentCardComponent(R.drawable.visa,"*********2109")
                        Spacer(modifier= Modifier.height(10.dp))

                    }

                }
                item{
                    CheckoutScreen()
                }

            }
        }
    )
}
@Composable
fun CheckoutScreen() {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(10.dp))

        ButtonComponent(
            onClick = { showDialog = true },
            submitString = "Continue"
        )
    }

    if (showDialog) {
        PaymentSuccessDialog(
            onDismissRequest = { showDialog = false }
        )
    }
}
