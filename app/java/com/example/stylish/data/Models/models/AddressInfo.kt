package com.example.stylish.data.Models.models


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressInfo(
    var pincode: String = "450116",
    var address: String = "216 St Paul's Rd",
    var city: String = "London",
    var state: String = "N1 2LL",
    var country: String = "United Kingdom"
) : Parcelable