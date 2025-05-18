package com.example.stylish.data.Models.models

data class ShoppinglistItemModel(
    var image: String,
    var itemName: String,
    var variation: List<String>,
    var itemRate: Double,
    var itemPrice: Double
)
