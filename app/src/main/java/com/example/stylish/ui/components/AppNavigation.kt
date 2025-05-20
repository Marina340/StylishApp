package com.example.stylish.ui.components

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.example.settingscreen.SettingScreens.SettingsScreen
import com.example.stylish.OnboardingScreen
import com.example.stylish.domain.shared.LoginResponse
import com.example.stylish.data.Models.models.Productt
import com.example.stylish.data.local.isOnboardingCompleted
import com.example.stylish.data.local.setOnboardingCompleted
import com.example.stylish.presentation.pages.MainScreen
import com.example.stylish.presentation.pages.SearchScreen
import com.example.stylish.presentation.pages.ShoppingBagScreen
import com.example.stylish.presentation.widget.Screen
import com.example.stylish.ui.components.LoginComponents.PrefsManager
import com.example.stylish.ui.screens.HomeScreens.categoriesScreens.CategoriesScreen
import com.example.stylish.ui.screens.CheckoutScreens.Checkout
import com.example.stylish.ui.screens.LoginScreens.ForgotPasswordScreen
import com.example.stylish.ui.screens.LoginScreens.LoginScreen
import com.example.stylish.ui.screens.LoginScreens.RegisterScreen
import com.example.stylish.ui.screens.HomeScreens.ProductDetailScreen
import com.example.stylish.ui.screens.HomeScreens.categoriesScreens.ProductListScreen
import com.example.stylish.ui.screens.ProfileScreens.ChangePassword
import com.example.stylish.ui.screens.ProfileScreens.ProfileScreen
import com.example.stylish.ui.screens.BNBscreens.WishlistPage
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(context: Context = LocalContext.current) {
    var prefsManager = remember { PrefsManager(context) }
    val navController = rememberNavController()
    var startDestination by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Check onboarding flag
    LaunchedEffect(Unit) {
        val completed = isOnboardingCompleted(context)
        startDestination = if (prefsManager.isLoggedIn()) "main" else "login"
    }

    if (startDestination != null) {
        NavHost(navController, startDestination!!) {
            composable("onboarding") {
                OnboardingScreen(
                    onFinish = {
                        // ✅ Launch a coroutine properly
                        coroutineScope.launch {
                            setOnboardingCompleted(context)
                            navController.navigate("login") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    }
                )
            }
            composable("login") {
                LoginScreen(navController,prefsManager)
            }
            composable("forgetpassword") {
                ForgotPasswordScreen(navController,prefsManager)
            }
            composable("register") {
                RegisterScreen(navController, prefsManager)
            }
            composable("main") { backStackEntry ->
                var user = navController.previousBackStackEntry?.savedStateHandle?.get<LoginResponse>("user")
                MainScreen(navController, prefsManager, user)
            }
            composable("profile") { backStackEntry ->
                var user = navController.previousBackStackEntry?.savedStateHandle?.get<LoginResponse>("user")
                ProfileScreen(navController, prefsManager, user)

            }
            composable("changePassword") { backStackEntry ->
                val user = navController.previousBackStackEntry?.savedStateHandle?.get<LoginResponse>("user")
                ChangePassword(navController, prefsManager, user)
            }
            composable(Screen.ShoppingBagScreen.route) { ShoppingBagScreen(navController) }
            composable(Screen.WishListPage.route) { WishlistPage(navController) }
            composable(Screen.ShoppingScreen.route) { ShoppingBagScreen(navController) }
            composable(Screen.ShoppingScreen.route) { ShoppingBagScreen( navController ) }
            composable(Screen.SearchScreen.route) { SearchScreen( navController) }
            composable(Screen.Checkout.route) { Checkout(navController) }
//***************
            composable("categories") {
                CategoriesScreen(onCategoryClick = { category ->
                    navController.navigate("products/$category")
                })
            }
            composable("products/{category}") { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                ProductListScreen(
                    category = category, onProductClick = {}, navController
//                    category = category,
//                    onProductClick = { product ->
//                        navController.previousBackStackEntry?.savedStateHandle?.set("product", product)
//                        navController.navigate(Screen.ProductDetail.route)
//                    },
//                    navController
                )
            }
            // Product Detail Screen (accessible from anywhere in main flow)
//            composable(Screen.ProductDetail.route) {
//                val product = navController.previousBackStackEntry
//                    ?.savedStateHandle
//                    ?.get<Productt>("product")
//
//                if (product != null) {
//                    ProductDetailScreen(
//                        product = product,
//                        navController= navController,
//                    )
//                } else {
//                    Text("Product not found")
//                }
//            }
            // UPDATED destination
            composable(Screen.ProductDetail.route) {
                val product = navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Productt>("product")

                if (product != null) {
                    ProductDetailScreen(product = product, navController = navController)
                } else {
                    Text("Product not found")
                }
            }

            composable(Screen.SettingsScreen.route) { backStackEntry ->
                // جلب المستخدم من الحالة المحفوظة
                val user = navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<LoginResponse>("user")

                SettingsScreen(navController, prefsManager, user)
            }


//            // Group selection screen for categories
//            composable("categories") {
//                GroupSelectionScreen(
//                    onGroupSelected = { groupId ->
//                        navController.navigate("items/$groupId")
//                    }
//                )
//            }
            //            composable("products/{category}") { backStackEntry ->
//                val category = backStackEntry.arguments?.getString("category") ?: ""
//                ProductListScreen(category = category, onProductClick = { /* handle product click */ }, navController)
//            }
            //***************

//************************************************
//            composable("itemlist/{groupId}") { backStackEntry ->
//                val groupId = backStackEntry.arguments?.getString("groupId") ?: ""
//                ItemListScreen(groupId) { /* Handle item click */ }
//            }
            // Item list screen for selected group/category
//            composable(
//                route = "items/{groupId}",
//                arguments = listOf(navArgument("groupId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val groupId = backStackEntry.arguments?.getString("groupId") ?: ""
//                ItemListScreen(
//                    groupId = groupId,
//                    onItemClick = { item ->
//                        // Navigate to product details or similar
//                        navController.navigate(Screen.ProductDetail.route) {
//                            launchSingleTop = true
//                            restoreState = true
//                            popUpTo("items/$groupId") { inclusive = true }
//                        }
//                    }
//                )
//            }
//        }
//    } else {
//        // Show loading while checking onboarding flag
//        // CircularProgressIndicator() can be shown here
//    }
        }
    }
}