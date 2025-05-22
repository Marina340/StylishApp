package com.example.stylish.ui.screens.HomeScreens.categoriesScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.stylish.R
import com.example.stylish.domain.api.Category
import com.example.stylish.domain.api.api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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

///******************

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
///********************




// --- Category Row (Horizontal Chip List) ---
//@Composable
//fun CategoryRow(onCategoryClick: (String) -> Unit) {
//    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
//    var isLoading by remember { mutableStateOf(true) }
//    var error by remember { mutableStateOf<String?>(null) }
//
//    val allowedSlugs = listOf(
//        "womens-bags", "womens-dresses", "womens-jewellery", "womens-shoes", "womens-watches",
//        "mens-shirts", "mens-shoes", "mens-watches", "skin-care", "tops", "beauty"
//    )
//
//    // Map each slug to a drawable image resource
//    val categoryIcons = mapOf(
//        "womens-bags" to R.drawable.wbags,
//        "womens-dresses" to R.drawable.dresss,
//        "womens-jewellery" to R.drawable.gifts,
//        "womens-shoes" to R.drawable.wshoes,
//        "womens-watches" to R.drawable.watchess,
//        "mens-shirts" to R.drawable.mshirts,
//        "mens-shoes" to R.drawable.mshoes,
//        "mens-watches" to R.drawable.watchess,
//        "skin-care" to R.drawable.skincare,
//        "tops" to R.drawable.topps,
//        "beauty" to R.drawable.beautty
//    )
//
//    LaunchedEffect(Unit) {
//        try {
//            val allCategories = withContext(Dispatchers.IO) {
//                api.getCategories()
//            }
//            categories = allCategories.filter { it.slug in allowedSlugs }
//        } catch (e: Exception) {
//            error = e.message ?: "Failed to load categories"
//        } finally {
//            isLoading = false
//        }
//    }
//
//    when {
//        isLoading -> {
//            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        }
//        error != null -> {
//            Text(
//                text = error!!,
//                modifier = Modifier.padding(16.dp),
//                color = MaterialTheme.colorScheme.error
//            )
//        }
//        else -> {
//            LazyRow(
//                modifier = Modifier.padding(vertical = 8.dp),
//                horizontalArrangement = Arrangement.spacedBy(12.dp),
//                contentPadding = PaddingValues(horizontal = 16.dp)
//            ) {
//                items(categories) { category ->
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        modifier = Modifier.clickable { onCategoryClick(category.slug) }
//                    ) {
//                        val iconRes = categoryIcons[category.slug]
//                        if (iconRes != null) {
//                            Image(
//                                painter = painterResource(id = iconRes),
//                                contentDescription = category.name,
//                                modifier = Modifier
//                                    .size(60.dp)
//                                    .clip(CircleShape)
//                                    .background(MaterialTheme.colorScheme.surface)
//                                    .padding(8.dp)
//                            )
//                        }
//
//                        Text(
//                            text = category.name,
//                            style = MaterialTheme.typography.bodySmall,
//                            textAlign = TextAlign.Center,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            modifier = Modifier
//                                .padding(top = 4.dp)
//                                .width(80.dp) // Fixed width for consistent truncation
//                        )
//
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//
//


// --- Categories Screen ---
//@Composable
//fun CategoriesScreen(onCategoryClick: (String) -> Unit) {
//    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
//    var isLoading by remember { mutableStateOf(true) }
//    var error by remember { mutableStateOf<String?>(null) }
//
//    val allowedSlugs = listOf(
//        "womens-bags", "womens-dresses", "womens-jewellery", "womens-shoes", "womens-watches",
//        "mens-shirts", "mens-shoes", "mens-watches", "skin-care", "tops", "beauty"
//    )
//
//    LaunchedEffect(Unit) {
//        try {
//            val allCategories = withContext(Dispatchers.IO) {
//                api.getCategories()
//            }
//            categories = allCategories.filter { it.slug in allowedSlugs }
//        } catch (e: Exception) {
//            error = e.message
//        } finally {
//            isLoading = false
//        }
//    }
//
//    when {
//        isLoading -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        }
//        error != null -> {
//            Text(
//                text = error ?: "Unknown error",
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//        else -> {
//            Column(Modifier.fillMaxSize().padding(16.dp)) {
//                Text("Categories", style = MaterialTheme.typography.titleLarge)
//                Spacer(Modifier.height(8.dp))
//                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                    items(categories) { category ->
//                        Card(
//                            modifier = Modifier
//                                .clickable { onCategoryClick(category.slug) }
//                        ) {
//                            Text(
//                                category.name,
//                                modifier = Modifier.padding(12.dp)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}