package com.example.firebasecompras.data.domain.model

data class Products(
    val description: String? = null,
    val nome: String? = null,
    val price: Double = 0.0,
    val stock: Int = 0
)