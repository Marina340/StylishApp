package com.example.stylish.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stylish.domain.api.ProductsViewModel

class ProductsViewModelFactory(
    private val favoriteDataStore: FavoriteDataStore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductsViewModel(favoriteDataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
