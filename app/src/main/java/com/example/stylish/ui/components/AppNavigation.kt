package com.example.stylish.ui.components

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.stylish.OnboardingScreen
import com.example.stylish.data.isOnboardingCompleted
import com.example.stylish.data.setOnboardingCompleted
import com.example.stylish.presentation.pages.HomeScreen
import com.example.stylish.presentation.pages.MainScreen
import com.example.stylish.presentation.widget.Productt
import com.example.stylish.presentation.widget.Screen
import com.example.stylish.ui.screens.CategoriesScreen
import com.example.stylish.ui.screens.LoginScreens.ForgotPasswordScreen
import com.example.stylish.ui.screens.LoginScreens.LoginScreen
import com.example.stylish.ui.screens.LoginScreens.RegisterScreen
import com.example.stylish.ui.screens.ProductDetailScreen
import com.example.stylish.ui.screens.ProductListScreen
import com.example.stylish.ui.screens.ProfileScreens.ProfileScreen
//import com.example.stylish.ui.screens.GroupSelectionScreen
//import com.example.stylish.ui.screens.ItemListScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(context: Context = LocalContext.current) {
    val navController = rememberNavController()
    var prefsManager = remember { PrefsManager(context) }
    var startDestination by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Check onboarding flag
    LaunchedEffect(Unit) {
        val completed = isOnboardingCompleted(context)
        startDestination = if (completed) "login" else "onboarding"
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
                LoginScreen(navController, prefsManager)
            }
            composable("forgetpassword") {
                ForgotPasswordScreen(navController, prefsManager)
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
        }
    } else {
        // Show loading while checking onboarding flag
//        androidx.compose.material3.CircularProgressIndicator()
    }
}
//***************
            composable("categories") {
                CategoriesScreen(onCategoryClick = { category ->
                    navController.navigate("products/$category")
                })
            }
            composable("products/{category}") { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                ProductListScreen(category = category, onProductClick = {})
            }
            // Product Detail Screen (accessible from anywhere in main flow)
            composable(Screen.ProductDetail.route) {
                val product = navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Productt>("product")

                if (product != null) {
                    ProductDetailScreen(
                        product = product,
                        navController= navController,
                    )
                } else {
                    Text("Product not found")
                }
            }

            composable("products/{category}") { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                ProductListScreen(category = category, onProductClick = { /* handle product click */ })
            }
//            // Group selection screen for categories
//            composable("categories") {
//                GroupSelectionScreen(
//                    onGroupSelected = { groupId ->
//                        navController.navigate("items/$groupId")
//                    }
//                )
//            }
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