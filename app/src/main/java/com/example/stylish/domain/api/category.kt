package com.example.stylish.domain.api  // Changed to lowercase 'models'

import androidx.annotation.DrawableRes
import com.example.stylish.R
import java.util.Locale

data class Category(
    val apiName: String,
    val displayName: String,
    @DrawableRes val imageRes: Int  // Added annotation for resource validation
)

private val categoryDisplayMap: Map<String, Pair<String, Int>> = mapOf(
    "smartphones" to Pair("Electronics", R.drawable.men),
    "laptops" to Pair("Electronics", R.drawable.men),
    "fragrances" to Pair("Beauty", R.drawable.beauty),
    "skincare" to Pair("Beauty", R.drawable.beauty),
    "groceries" to Pair("Groceries", R.drawable.men),
    "home-decoration" to Pair("Home", R.drawable.men),
    "furniture" to Pair("Furniture", R.drawable.men),
    "tops" to Pair("Tops", R.drawable.fashion),
    "womens-dresses" to Pair("Dresses", R.drawable.women),
    "womens-shoes" to Pair("Shoes", R.drawable.women),
    "mens-shirts" to Pair("Shirts", R.drawable.men),
    "mens-shoes" to Pair("Shoes", R.drawable.men),
    "mens-watches" to Pair("Watches", R.drawable.men),
    "womens-watches" to Pair("Watches", R.drawable.men),
    "womens-bags" to Pair("Bags", R.drawable.men),
    "womens-jewellery" to Pair("Jewellery", R.drawable.men),
    "sunglasses" to Pair("Sunglasses", R.drawable.men),
    "automotive" to Pair("Auto", R.drawable.men),
    "motorcycle" to Pair("Motorcycle", R.drawable.men),
    "lighting" to Pair("Lighting", R.drawable.men)
)

fun String.toDisplayName(): String =
    replace("-", " ")
        .split(" ")
        .joinToString(" ") {
            it.replaceFirstChar { char -> char.uppercase(Locale.ROOT) }
        }

fun createCategoryFromApiName(apiName: String): Category {
    val (displayName, imageRes) = categoryDisplayMap[apiName]
        ?: Pair(apiName.toDisplayName(), R.drawable.edit_square_icon)
    return Category(apiName, displayName, imageRes)
}