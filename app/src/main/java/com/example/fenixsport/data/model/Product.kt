package com.example.fenixsport.data.model

import androidx.annotation.DrawableRes

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    @param:DrawableRes val image: Int,
    val category: String
)