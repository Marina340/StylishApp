package com.example.stylish.data.Models.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Productt(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("rating") val rating: Double,
    @SerializedName("stock") val stock: Int,
    @SerializedName("thumbnail") val thumbnail: String,
    val category: String, // Added category for clarity
    val isFavorite: Boolean = false,
    val quantity: Int = 1
) : Parcelable


//data class Product(
//    val id: Int,
//    val title: String,
//    val description: String,
//    val price: Double,
//    val rating: Float,
//    val thumbnail: String
//)