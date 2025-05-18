package com.example.stylish.domain.shared

import android.os.Parcelable
import com.example.stylish.data.Models.models.AddressInfo
import kotlinx.parcelize.Parcelize


@Parcelize
data class LoginResponse(
    val id: Int,
    var username: String,
    var email: String,
    var firstName: String,
    var lastName: String,
    var gender: String,
    var image: String,
    var token: String,
    var address: AddressInfo = AddressInfo()
): Parcelable