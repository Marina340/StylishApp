package com.example.stylish.data.Models

//data class Product(
//    val imageRes: Int,
//    val title: String,
//     val description: String,
//    val price: String,
//    val rating: Float,
//   val reviews: String
//)


data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Float,
    val thumbnail: String
)
