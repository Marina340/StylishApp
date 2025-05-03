package com.example.stylish.data.repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.stylish.domain.api.Category
import com.example.stylish.domain.api.createCategoryFromApiName

//
//class CategoryRepository {
//    private val apiService = ApiClient.apiService
//
//    suspend fun getCategories(): List<Category> = withContext(Dispatchers.IO) {
//        try {
//            apiService.getCategories().map { createCategoryFromApiName(it) }
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }
//}