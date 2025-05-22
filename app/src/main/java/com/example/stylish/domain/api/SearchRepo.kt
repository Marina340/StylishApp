package com.example.stylish.domain.api

import com.example.stylish.data.Models.models.Productt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyJsonApiSearchService {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 30,
        @Query("skip") skip: Int = 0,
        @Query("select") select: String? = null
    ): SearchProductsResponse

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("limit") limit: Int = 30
    ): SearchProductsResponse


    companion object {
        fun create(): DummyJsonApiSearchService {
            return Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DummyJsonApiSearchService::class.java)
        }
    }
}

data class SearchProductsResponse(
    val products: List<Productt>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
//class ProductsViewModel(
//    private val favoriteDataStore: FavoriteDataStore
//) : ViewModel() {
//
//    private val apiService = DummyJsonApiService.create()
//
//    var products by mutableStateOf<List<Productt>>(emptyList())
//        private set
//
//    var isLoading by mutableStateOf(true)
//        private set
//
//    var error by mutableStateOf<String?>(null)
//        private set
//
//    var favorites by mutableStateOf<List<Productt>>(emptyList())
//        private set
//
//    private val allowedCategories = listOf(
//        "womens-bags", "womens-dresses", "womens-jewellery", "womens-shoes", "womens-watches",
//        "mens-shirts", "mens-shoes", "mens-watches", "skin-care", "tops", "beauty"
//    )
//
//    init {
//        loadProducts()
//    }
//
//    fun getProductById(id: Int): Productt? {
//        return products.find { it.id == id }
//    }
//
//    fun loadProducts(category: String? = null) {
//        viewModelScope.launch {
//            isLoading = true
//            error = null
//
//            try {
//                val response = apiService.getProducts()
//                Log.d("ProductsViewModel", "Fetched ${response.products.size} products")
//
//                val favoriteIds = favoriteDataStore.getFavoriteIds()
//                Log.d("ProductsViewModel", "Favorite IDs: $favoriteIds")
//
//                val filteredProducts = if (category != null && category in allowedCategories) {
//                    response.products.filter { it.category == category }
//                } else {
//                    response.products
//                }
//
//                products = filteredProducts.map { product ->
//                    product.copy(isFavorite = favoriteIds.contains(product.id.toString()))
//                }
//
//                Log.d("ProductsViewModel", "After filtering, products count: ${products.size}")
//
//                favorites = products.filter { it.isFavorite }
//            } catch (e: Exception) {
//                error = e.message ?: "Unknown error occurred"
//                Log.e("ProductsViewModel", "Error loading products", e)
//            } finally {
//                isLoading = false
//            }
//        }
//    }
//
//    fun toggleFavorite(product: Productt) {
//        viewModelScope.launch {
//            favoriteDataStore.toggleFavorite(product.id.toString())
//
//            products = products.map {
//                if (it.id == product.id) it.copy(isFavorite = !it.isFavorite) else it
//            }
//
//            favorites = products.filter { it.isFavorite }
//        }
//    }
//}


