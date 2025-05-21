package com.example.stylish.presentation.widget

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.data.Models.models.Productt
import com.example.stylish.data.local.FavoriteDataStore
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
class ProductsViewModel(
    private val favoriteDataStore: FavoriteDataStore
) : ViewModel() {

    private val apiService = DummyJsonApiService.create()

    var products by mutableStateOf<List<Productt>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var favorites by mutableStateOf<List<Productt>>(emptyList())
        private set

    private val allowedCategories = listOf(
        "womens-bags", "womens-dresses", "womens-jewellery", "womens-shoes", "womens-watches",
        "mens-shirts", "mens-shoes", "mens-watches", "skin-care", "tops", "beauty"
    )

    init {
        loadProducts()
    }

    fun getProductById(id: Int): Productt? {
        return products.find { it.id == id }
    }

    fun loadProducts(category: String? = null) {
        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                val response = apiService.getProducts()
                Log.d("ProductsViewModel", "Fetched ${response.products.size} products")

                val favoriteIds = favoriteDataStore.getFavoriteIds()
                Log.d("ProductsViewModel", "Favorite IDs: $favoriteIds")

                val filteredProducts = if (category != null && category in allowedCategories) {
                    response.products.filter { it.category == category }
                } else {
                    response.products
                }

                products = filteredProducts.map { product ->
                    product.copy(isFavorite = favoriteIds.contains(product.id.toString()))
                }

                Log.d("ProductsViewModel", "After filtering, products count: ${products.size}")

                favorites = products.filter { it.isFavorite }
            } catch (e: Exception) {
                error = e.message ?: "Unknown error occurred"
                Log.e("ProductsViewModel", "Error loading products", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun toggleFavorite(product: Productt) {
        viewModelScope.launch {
            favoriteDataStore.toggleFavorite(product.id.toString())

            products = products.map {
                if (it.id == product.id) it.copy(isFavorite = !it.isFavorite) else it
            }

            favorites = products.filter { it.isFavorite }
        }
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
    object ProductDetail : Screen("productDetail/{productId}") {
        fun createRoute(productId: Int): String = "productDetail/$productId"
    }
}

//****************************