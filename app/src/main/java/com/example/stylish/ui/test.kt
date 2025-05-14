package com.example.stylish.presentation.widget

import android.os.Parcelable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlinx.parcelize.Parcelize
@Parcelize
data class Productt(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("rating") val rating: Double,
    @SerializedName("stock") val stock: Int,
    @SerializedName("thumbnail") val thumbnail: String,
    val category: String, // Added category for clarity
    val isFavorite: Boolean = false
) : Parcelable


data class ProductsResponse( @SerializedName("products") val products: List<Productt>, @SerializedName("total") val total: Int, @SerializedName("skip") val skip: Int, @SerializedName("limit") val limit: Int )

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
@Composable
fun ProductGridd(viewModel: ProductsViewModel = viewModel(), category: String? = null, onProductClick: (Productt) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().heightIn(min = 200.dp, max = 600.dp)) {
        viewModel.loadProducts(category) // Load products based on category

        when {
            viewModel.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading products...")
                }
            }
            viewModel.error != null -> {
                Text(
                    text = "Error: ${viewModel.error}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(viewModel.products.size) { index ->
                        ProductCardd(
                            product = viewModel.products[index],
                            onFavoriteClick = { viewModel.toggleFavorite(it) },
                            onProductClick = onProductClick
                        )
                    }
                }
            }
        }
    }
}


sealed class Screen(val route: String) {

    object ItemList : Screen("items/{groupId}")
    object ProductGrid : Screen("product_grid")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int): String = "product_detail/$productId"
    }
}

//****************************