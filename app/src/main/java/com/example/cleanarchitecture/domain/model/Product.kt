package com.example.cleanarchitecture.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val userId: String = "",
    val name: String = "",
    val price: Int = 0,
) : Parcelable
