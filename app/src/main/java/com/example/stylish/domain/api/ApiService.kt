package com.example.stylish.domain.api//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//
//data class CategoriesResponse(
//    val categories: List<String>,
//    val filteredProducts: List<Product>
//)
//interface DummyJsonApiService {
//    @GET("products/categories")
//    suspend fun getAllCategories(): List<String>
//
//    @GET("products")
//    suspend fun getAllProducts(): ProductsResponse
//
//    companion object {
//        private const val BASE_URL = "https://dummyjson.com/"
//
//        fun create(): DummyJsonApiService {
//            return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(DummyJsonApiService::class.java)
//        }
//    }
//}