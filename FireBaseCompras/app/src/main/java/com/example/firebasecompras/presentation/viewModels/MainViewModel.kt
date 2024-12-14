package com.example.firebasecompras.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.firebasecompras.data.domain.model.Products
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    //estado dos produtos e carrinhos
    private val productsList = MutableStateFlow<List<Products>>(emptyList())
    private val cartsList = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    //listas de produtos e carrinhos
    val products: StateFlow<List<Products>> = productsList
    val carts: StateFlow<List<Pair<String, String>>> = cartsList

    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    //corre fetchProducts() e fetchUserCarts
    init {
        fetchProducts()
        fetchUserCarts()
    }

    //busca os produtos no firestore
    private fun fetchProducts() {
        firestore.collection("Products")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("MainViewModel", "Listen failed.", e)
                    return@addSnapshotListener //atualiza quando ha atualizacoes aos produtos
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val productList = snapshot.documents.mapNotNull { document ->
                        document.toObject(Products::class.java)
                    }
                    productsList.value = productList //obtem os produtos
                } else {
                    Log.d("MainViewModel", "No products found")
                }
            }
    }

    //busca os carrinhos do utilizador atual no firestore
    private fun fetchUserCarts() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("Carts")
            .whereEqualTo("ownerId", userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("MainViewModel", "Error listening to user carts", e)
                    return@addSnapshotListener //atualiza se houver alteracoes no firestore
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val carts = snapshot.documents.map { document ->
                        document.id to (document.getString("name") ?: "Unnamed Cart")
                    }
                    cartsList.value = carts // Atualiza a lista de carrinhos
                } else {
                    Log.d("MainViewModel", "No carts found")
                }
            }
    }

    //adicionar um produto a um carrinho no firestore - cartItems
    fun addToCart(cartId: String, productId: String, quantity: Int) {
        val cartItemData = hashMapOf(
            "cartId" to cartId,
            "productId" to productId,
            "quantity" to quantity
        )

        firestore.collection("CartItems")
            .add(cartItemData)
            .addOnSuccessListener {
                Log.d("MainViewModel", "Item added to CartItems: $cartItemData")
            }
            .addOnFailureListener { e ->
                Log.e("MainViewModel", "Error adding to CartItems: $cartItemData", e)
            }
    }

    //busca o ID de um produto atraves do nome
    fun fetchProductIdByName(productName: String, onSuccess: (String) -> Unit) {
        firestore.collection("Products")
            .whereEqualTo("nome", productName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val productId = querySnapshot.documents.first().id
                    onSuccess(productId)
                }
            }
            .addOnFailureListener { e ->
                Log.e("MainViewModel", "Error fetching product ID", e)
            }
    }
}