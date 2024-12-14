package com.example.firebasecompras.data.domain.model

data class Users(
    val email: String,
    val name: String? = null,
    val sharedCarts: List<String>? = null 
) 