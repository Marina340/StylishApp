package com.example.stylish.ui.components

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.settingscreen.SettingScreens.SettingsScreen
import com.example.stylish.OnboardingScreen
import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.data.Models.models.Productt
import com.example.stylish.data.local.isOnboardingCompleted
import com.example.stylish.data.local.setOnboardingCompleted
import com.example.stylish.presentation.pages.MainScreen
import com.example.stylish.presentation.pages.SearchScreen
import com.example.stylish.presentation.pages.ShoppingBagScreen
import com.example.stylish.presentation.widget.Screen
import com.example.stylish.ui.components.LoginComponents.PrefsManager
import com.example.stylish.ui.screens.HomeScreens.ProductDetailScreen
import com.example.stylish.ui.screens.HomeScreens.categoriesScreens.CategoriesScreen
import com.example.stylish.ui.screens.HomeScreens.categoriesScreens.ProductListScreen
import com.example.stylish.ui.screens.CheckoutScreens.Checkout
import com.example.stylish.ui.screens.LoginScreens.ForgotPasswordScreen
import com.example.stylish.ui.screens.LoginScreens.LoginScreen
import com.example.stylish.ui.screens.LoginScreens.RegisterScreen
import com.example.stylish.ui.screens.ProfileScreens.ChangePassword
import com.example.stylish.ui.screens.ProfileScreens.ProfileScreen
import com.example.stylish.ui.screens.BNBscreens.WishlistPage
import com.example.stylish.data.local.FavoriteDataStore
import com.example.stylish.presentation.widget.ProductsViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(context: Context = LocalContext.current) {
    val prefsManager = remember { PrefsManager(context) }
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    val favoriteDataStore = remember { FavoriteDataStore(context) }
    val productsViewModel = remember { ProductsViewModel(favoriteDataStore) }

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
            composable(Screen.Checkout.route) {backStackEntry -> val user = navController.previousBackStackEntry?.savedStateHandle?.get<LoginResponse>("user")
                Checkout(navController,prefsManager) }
//***************
            composable("categories") {
                CategoriesScreen(onCategoryClick = { category ->
                    navController.navigate("products/$category")
                })
            }
            composable("products/{category}") { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                ProductListScreen(
                    category = category,
                    onProductClick = { product ->
                        navController.navigate("productDetail/${product.id}")
                    },
                    navController = navController
                )
            }
            composable(
                route = "productDetail/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") ?: -1

                // Pass productId & required objects down to ProductDetailScreen
                ProductDetailScreen(
                    productId = productId,
                    navController = navController,
                    favoriteDataStore = favoriteDataStore,
                    viewModel = productsViewModel
                )
            }
            composable(Screen.SettingsScreen.route) {
                val user = navController.previousBackStackEntry?.savedStateHandle?.get<LoginResponse>("user")
                SettingsScreen(navController, prefsManager, user)
            }
        }
    } else {
        Text("Loading...")
    }
}

