package com.example.stylish.presentation.widget

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.data.local.FavoriteDataStore
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import com.example.stylish.data.Models.models.Productt
import com.example.stylish.domain.api.DummyJsonApiSearchService
import retrofit2.http.Query


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

sealed class Screen(val route: String) {

    object ShoppingBagScreen : Screen("product")
    object WishListPage : Screen("favourite")
    object ShoppingScreen : Screen("order")
    object SettingsScreen : Screen("setting")
    object Profile : Screen("profile")
    object SearchScreen : Screen("SearchScreen")
    object Checkout : Screen("checkout")
//***********************
    object ItemList : Screen("items/{groupId}")
    object ProductGrid : Screen("product_grid")
    object ProductDetail : Screen("productDetail/{productId}") {
        fun createRoute(productId: Int): String = "productDetail/$productId"
    }
}

//****************************