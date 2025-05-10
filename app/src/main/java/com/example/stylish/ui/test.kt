package com.example.stylish.presentation.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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

// ----------------------------
// Data Models
// ----------------------------
data class Productt(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("rating") val rating: Double,
    @SerializedName("stock") val stock: Int,
    @SerializedName("thumbnail") val thumbnail: String,
    val isFavorite: Boolean = false // Make it immutable and use state management
)

data class ProductsResponse(
    @SerializedName("products") val products: List<Productt>,
    @SerializedName("total") val total: Int,
    @SerializedName("skip") val skip: Int,
    @SerializedName("limit") val limit: Int
)

// ----------------------------
// API Service
// ----------------------------
interface DummyJsonApiService {
    @GET("products")
    suspend fun getProducts(): ProductsResponse

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
fun Rating(rating: Float) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                tint = if (index < rating) Color.Yellow else Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

// ----------------------------
// Product Grid Page
// ----------------------------
@Composable
fun ProductGridd(viewModel: ProductsViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp, max = 600.dp)
    ) {
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
                            product = product,
                            onFavoriteClick = { viewModel.toggleFavorite(it) }
                        )
                    }
                }
            }
        }
    }
}
// ----------------------------
// Wishlist Page
// ----------------------------
