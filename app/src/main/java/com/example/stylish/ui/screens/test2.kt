
package com.example.stylish.ui.screens
import com.example.stylish.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import coil.compose.AsyncImage
import coil.size.Size
import com.example.stylish.data.Models.Product
import com.example.stylish.presentation.widget.RatingBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import androidx.compose.material.icons.filled.ArrowBack

// --- Data Models ---
data class ProductResponse(
    val products: List<Product>
)

data class Category(val slug: String, val name: String, val url: String)

// --- Retrofit API ---
interface ApiService {
    @GET("products/categories")
    suspend fun getCategories(): List<Category>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): ProductResponse
}

// --- Retrofit Instance ---
val api = Retrofit.Builder()
    .baseUrl("https://dummyjson.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(ApiService::class.java)

// --- Categories Screen ---
@Composable
fun CategoriesScreen(onCategoryClick: (String) -> Unit) {
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val allowedSlugs = listOf(
        "womens-bags", "womens-dresses", "womens-jewellery", "womens-shoes", "womens-watches",
        "mens-shirts", "mens-shoes", "mens-watches", "skin-care", "tops", "beauty"
    )

    LaunchedEffect(Unit) {
        try {
            val allCategories = withContext(Dispatchers.IO) {
                api.getCategories()
            }
            categories = allCategories.filter { it.slug in allowedSlugs }
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            Text(
                text = error ?: "Unknown error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
        else -> {
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                Text("Categories", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categories) { category ->
                        Card(
                            modifier = Modifier
                                .clickable { onCategoryClick(category.slug) }
                        ) {
                            Text(
                                category.name,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


// --- Product Item Card ---
@Composable
fun ProductItemCard(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}  // Add this parameter
) {
    // Default/placeholder values for missing product information
    val placeholderImageRes = R.drawable.custom_back_arrow
    val placeholderDescription = "Product description not available"
    val placeholderPrice = "$19.99"
    val placeholderRating = 4.2f
    val placeholderReviews = "(128 reviews)"

    Card(
        modifier = modifier
            .width(180.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),  // Add clickable modifier
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        // Rest of the implementation remains the same
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                text = placeholderDescription,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = "$${product.price}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Red)
            RatingBar(product.rating)
            Text(text = "(${product.rating} stars)", fontSize = 12.sp, color = Color.Gray)

//            Row(verticalAlignment = Alignment.CenterVertically) {
//                RatingBar(placeholderRating)
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(text = placeholderReviews, fontSize = 12.sp, color = Color.Gray)
//            }
        }
    }
}
// --- Product List Screen ---
@Composable
fun ProductListScreen(category: String, onProductClick: (Product) -> Unit , navController : NavController) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(category) {
        try {
            products = withContext(Dispatchers.IO) {
                api.getProductsByCategory(category).products
            }

        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            Text(
                text = error ?: "Failed to load products",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
        else -> {
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                Row(){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Spacer(Modifier.width(15.dp))
                    Text("$category", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(products) { product ->
                        ProductItemCard(product = product) {
                            onProductClick(product)
                        }
                    }
                }

                }
            }
        }
    }







// --- Category Row (Horizontal Chip List) ---
@Composable
fun CategoryRow(onCategoryClick: (String) -> Unit) {
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val allowedSlugs = listOf(
        "womens-bags", "womens-dresses", "womens-jewellery", "womens-shoes", "womens-watches",
        "mens-shirts", "mens-shoes", "mens-watches", "skin-care", "tops", "beauty"
    )

    // Map each slug to a drawable image resource
    val categoryIcons = mapOf(
        "womens-bags" to R.drawable.wbags,
        "womens-dresses" to R.drawable.dresss,
        "womens-jewellery" to R.drawable.gifts,
        "womens-shoes" to R.drawable.wshoes,
        "womens-watches" to R.drawable.watchess,
        "mens-shirts" to R.drawable.mshirts,
        "mens-shoes" to R.drawable.mshoes,
        "mens-watches" to R.drawable.watchess,
        "skin-care" to R.drawable.skincare,
        "tops" to R.drawable.topps,
        "beauty" to R.drawable.beautty
    )

    LaunchedEffect(Unit) {
        try {
            val allCategories = withContext(Dispatchers.IO) {
                api.getCategories()
            }
            categories = allCategories.filter { it.slug in allowedSlugs }
        } catch (e: Exception) {
            error = e.message ?: "Failed to load categories"
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            Text(
                text = error!!,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }
        else -> {
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(categories) { category ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { onCategoryClick(category.slug) }
                    ) {
                        val iconRes = categoryIcons[category.slug]
                        if (iconRes != null) {
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = category.name,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(8.dp)
                            )
                        }

                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .width(80.dp) // Fixed width for consistent truncation
                        )

                    }
                }
            }
        }
    }
}





