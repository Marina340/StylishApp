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
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.stylish.data.FavoriteDataStore
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
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

data class ProductsResponse(
    @SerializedName("products") val products: List<Productt>,
    @SerializedName("total") val total: Int,
    @SerializedName("skip") val skip: Int,
    @SerializedName("limit") val limit: Int
)

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

// ----------------------------
// ViewModel
// ----------------------------
class ProductsViewModel(private val favoriteDataStore: FavoriteDataStore) : ViewModel() {
    private val apiService = DummyJsonApiService.create()

    var products by mutableStateOf<List<Productt>>(emptyList())
    var isLoading by mutableStateOf(true)
    var error by mutableStateOf<String?>(null)
    private val _favorites = MutableStateFlow<List<Productt>>(emptyList()) // Private mutable state
    val favorites: StateFlow<List<Productt>> = _favorites // Public immutable state

    private val allowedCategories = listOf(
        "womens-bags", "womens-dresses", "womens-jewellery", "womens-shoes", "womens-watches",
        "mens-shirts", "mens-shoes", "mens-watches", "skin-care", "tops", "beauty"
    )

    init {
        loadProducts()
    }

    // Observe favorite product IDs and update the favorite list accordingly
    private fun observeFavorites() {
        viewModelScope.launch {
            favoriteDataStore.favoriteIds.collect { ids -> // Collect the favorite IDs
                products = products.map { product ->
                    product.copy(isFavorite = ids.contains(product.id.toString())) // Update each product's isFavorite flag
                }
                _favorites.value = products.filter { it.isFavorite } // Update the favorites state
            }
        }
    }


    // Load products from the API
    fun loadProducts() {
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
                val favoriteIds = favoriteDataStore.getFavoriteIds()

                products = response.products.map {
                    it.copy(isFavorite = favoriteIds.contains(it.id.toString()))
                }

                isLoading = false
                _favorites.value = products.filter { it.isFavorite }
                observeFavorites()
            } catch (e: Exception) {
                error = e.message ?: "Unknown error occurred"
                isLoading = false
            }
        }
    }

    // Toggle the favorite status of a product
// Inside the ViewModel, ensure that _favorites is updated after toggling favorites.
    fun toggleFavorite(product: Productt) {
        viewModelScope.launch {
            favoriteDataStore.toggleFavorite(product.id) // Toggle favorite in DataStore
        }
    }


}

// ----------------------------
// Product Card
// ----------------------------
@Composable
fun ProductCardd(
    product: Productt,
    onFavoriteClick: (Productt) -> Unit
) {
    var isFavorite by remember { mutableStateOf(product.isFavorite) }
    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box {
                Image(
                    painter = rememberImagePainter(product.thumbnail),
                    contentDescription = product.title,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = {
                        onFavoriteClick(product) // Just call the ViewModel handler
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (product.isFavorite) Color.Red else Color.Gray
                    )
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                text = product.description,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "₹${product.price}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Rating(rating = product.rating.toFloat())
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${product.stock} reviews",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
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
                        val product = viewModel.products[index]
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
// ----------------------------
// Wishlist Page
// ----------------------------
