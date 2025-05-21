//package com.example.stylish.data
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.stylish.presentation.widget.ProductsViewModel
//
//class ProductsViewModelFactory(private val favoriteDataStore: FavoriteDataStore) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
//            return ProductsViewModel(favoriteDataStore) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
