package com.example.stylish.presentation.widget

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.data.Models.models.Productt
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


data class ProductsResponse(@SerializedName("products") val products: List<Productt>, @SerializedName("total") val total: Int, @SerializedName("skip") val skip: Int, @SerializedName("limit") val limit: Int )

interface DummyJsonApiService { @GET("products") suspend fun getProducts(): ProductsResponse

    companion object {
        private const val BASE_URL = "https://dummyjson.com/"
        fun create(): DummyJsonApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DummyJsonApiService::class.java)
        }
    }

}

class ProductsViewModel : ViewModel() {
    private val apiService = DummyJsonApiService.create()

    var products by mutableStateOf<List<Productt>>(emptyList())
    var isLoading by mutableStateOf(true)
    var error by mutableStateOf<String?>(null)
    var favorites by mutableStateOf<List<Productt>>(emptyList())

    private val allowedCategories = listOf(
        "womens-bags", "womens-dresses", "womens-jewellery", "womens-shoes", "womens-watches",
        "mens-shirts", "mens-shoes", "mens-watches", "skin-care", "tops", "beauty"
    )

    init {
        loadProducts()
    }

    fun loadProducts(category: String? = null) {
        viewModelScope.launch {
            try {
                val response = apiService.getProducts()
                val filteredProducts = if (category != null) {
                    // Filter products by category (assuming category is in title or description)
                    response.products.filter { it.category == category }
                } else {
                    response.products
                }

                products = filteredProducts
                isLoading = false
            } catch (e: Exception) {
                error = e.message ?: "Unknown error occurred"
                isLoading = false
            }
        }
    }

    fun toggleFavorite(product: Productt) {
        products = products.map {
            if (it.id == product.id) {
                it.copy(isFavorite = !it.isFavorite)
            } else {
                it
            }
        }
        favorites = products.filter { it.isFavorite }
    }
}


sealed class Screen(val route: String) {

    object ShoppingBagScreen : Screen("product")
    object WishListPage : Screen("favourite")
    object ShoppingScreen : Screen("order")
    object SettingsScreen : Screen("setting")
    object Profile : Screen("profile")
    object SearchScreen : Screen("search")
    object Checkout : Screen("checkout")

    //  object Profile : Screen("logout")
//***********************
    object ItemList : Screen("items/{groupId}")
    object ProductGrid : Screen("product_grid")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int): String = "product_detail/$productId"
    }
}

//****************************