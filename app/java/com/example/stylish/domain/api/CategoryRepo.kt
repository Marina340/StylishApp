
package com.example.stylish.domain.api
import com.example.stylish.data.Models.models.Productt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// --- Data Models ---
data class ProductResponse(
    val products: List<Productt>
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






