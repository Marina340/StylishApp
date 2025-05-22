package com.example.stylish.domain.api

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.data.Models.models.Productt
import com.example.stylish.data.local.FavoriteDataStore
import com.example.stylish.presentation.widget.DummyJsonApiService
import kotlinx.coroutines.launch

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

    fun searchProducts(query: String) {
        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                // Use the new DummyJsonApiSearchService
                val searchApiService = DummyJsonApiSearchService.create()
                val response = searchApiService.searchProducts(query)

                val favoriteIds = favoriteDataStore.getFavoriteIds()
                products = response.products.map { product ->
                    product.copy(isFavorite = favoriteIds.contains(product.id.toString()))
                }
                favorites = products.filter { it.isFavorite }
            } catch (e: Exception) {
                error = e.message ?: "Unknown error occurred"
                Log.e("ProductsViewModel", "Error searching products", e)
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
