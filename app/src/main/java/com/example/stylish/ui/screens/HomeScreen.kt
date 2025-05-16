package com.example.stylish.presentation.pages

import CustomTopBar
import SidebarUI
import com.example.stylish.presentation.widget.DealsSection
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stylish.presentation.widget.ProductGridd
import com.example.stylish.presentation.widget.ProductHorizontalList
import com.example.stylish.presentation.widget.Screen
import com.example.stylish.ui.screens.CategoryRow
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.ui.components.Homepage.BannerSection

//import com.example.stylish.ui.screens.GroupSelectionScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, user: LoginResponse?) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            SidebarUI(navController = navController, currentRoute = currentRoute , user)
        }
    )
    {
        Scaffold(
            topBar = {
                CustomTopBar(
                    navController = navController,
                    user,
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding), // Ensure content doesn't overlap with AppBar
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                item {
                    Column {
                        Text(
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                            text = "Categories",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        CategoryRow(
                            onCategoryClick = { categorySlug ->
                                navController.navigate("products/$categorySlug")
                            }
                        )
                    }
                }
                item { BannerSection(    navController = navController) }
                item {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Best Seller",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                }
                item {
                    ProductHorizontalList(onProductClick = { product ->
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("product", product)
                        navController.navigate(Screen.ProductDetail.route)
                    })
                }
                item { DealsSection( navController) }
                item {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Products",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                item {
                    ProductGridd(
                        onProductClick = { product ->
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("product", product)
                            navController.navigate(Screen.ProductDetail.route)
                        }
                    )
                }

            }

        }
    }
}

