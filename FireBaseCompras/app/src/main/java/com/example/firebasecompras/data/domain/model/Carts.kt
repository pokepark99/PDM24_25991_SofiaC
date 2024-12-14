package com.example.firebasecompras.data.domain.model

import java.util.Date

data class Carts(
    val name: String? = null,
    val createdAt: Date? = null,
    val ownerId: String? = null,
    val sharedWith: List<String>? = null,
    val updatedAt: Date? = null
)