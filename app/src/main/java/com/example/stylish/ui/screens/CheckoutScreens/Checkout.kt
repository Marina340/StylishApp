package com.example.stylish.ui.screens.CheckoutScreens

import CartManager
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stylish.R
import com.example.stylish.ui.components.PaymentCardComponent.PaymentCardComponent
import com.example.stylish.ui.components.PaymentCardComponent.PaymentSuccessDialog
import com.example.stylish.ui.components.PaymentCardComponent.ShoppingListItemComponent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.data.Models.models.ShoppinglistItemModel
import com.example.stylish.ui.components.LoginComponents.ButtonComponent
import com.example.stylish.ui.components.LoginComponents.PrefsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceAsColor")
@Composable
fun Checkout( navController: NavController,prefsManager: PrefsManager) {
    val context = LocalContext.current
    val cartManager = remember { CartManager(context) }
    val username = remember { prefsManager.getLoggedInUsername() }
    val user = remember(username) {
        username?.let { prefsManager.getUserProfile(it) }
    }

    val cartItems by cartManager.cartItems.collectAsState(initial = emptyList())
    val shoppingListFromCart = cartItems.map { product ->
        ShoppinglistItemModel(
            image = product.thumbnail ?:"",  // fallback if null
            itemName = product.title ?: "Unknown",                // String
            variation =
//            product.variations ?:
            listOf("N/A"),     // List<String>
            itemRate = product.rating ?: 0.0,                    // Double
            itemPrice = product.price ?: 0.0
        )
    }

    val totalAmount = cartItems.sumOf { it.price?.toInt() ?: 0 }


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
                    IconButton(onClick = { navController.navigateUp() }) {
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
                modifier = Modifier
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
                    val addressText = "${user?.address?.pincode} ${user?.address?.address}, ${user?.address?.state} : ${user?.address?.city}"
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
                                    text =addressText,
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
                                IconButton(onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
                                    navController.navigate("profile") }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.add_circle_icon),
                                    contentDescription = "Add icon",
                                    tint = Color.Black,
                                    modifier = Modifier.size(26.dp)
                                )
                            }}
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

                items(count = shoppingListFromCart.size) { index ->
                    ShoppingListItemComponent(shoppingListFromCart[index])
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
                                text = totalAmount.toString(),
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
                                text = "$3",
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
                                text = (totalAmount+70).toString(),
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
    val context = LocalContext.current
    val cartManager = remember { CartManager(context) }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(10.dp))

        ButtonComponent(
            onClick = { showDialog = true },
            submitString = "Continue"
        )
    }

    if (showDialog) {
        PaymentSuccessDialog(
            onDismissRequest = {
                showDialog = false
                CoroutineScope(Dispatchers.IO).launch {
                    cartManager.clearCart()
                }
            }
        )
    }
}

