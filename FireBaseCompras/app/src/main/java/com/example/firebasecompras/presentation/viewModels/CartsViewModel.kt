package com.example.firebasecompras.presentation.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.firebasecompras.data.domain.model.CartItems
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class CartsViewModel : ViewModel() {
    //partilhar carrinho
    fun shareCart(cartId: String, userEmail: String) {
        val db = Firebase.firestore

        // encontrar utilizador atraves do email
        db.collection("users")
            .whereEqualTo("email", userEmail)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    // retornar o id do utilizador
                    val userIdToShareWith = querySnapshot.documents[0].id

                    // addicionar o id do utilizador a sharedWith
                    db.collection("Carts")
                        .document(cartId)
                        .update("sharedWith", FieldValue.arrayUnion(userIdToShareWith))
                        .addOnSuccessListener {
                            Log.d(TAG, "Cart successfully shared with $userEmail!")
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Error sharing cart", e)
                        }
                } else {
                    Log.e(TAG, "Email not found in Firestore users collection: $userEmail")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error querying email in Firestore users collection: $userEmail", e)
            }
    }

    //Renomear carrinho
    fun renameCart(cartId: String, newCartName: String) {
        val db = Firebase.firestore

        // Atualizar no firestore
        db.collection("Carts")
            .document(cartId)
            .update("name", newCartName.ifEmpty { "Unnamed Cart" }) // "Unnamed Cart" como default
            .addOnSuccessListener {
                Log.d(TAG, "Cart renamed successfully!")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error renaming cart", e)
            }
    }

    // remover produto do carrinho
    fun removeProductFromCart(product: CartItems) {
        val db = Firebase.firestore

        product.productId?.let { productId ->
            db.collection("CartItems")
                .whereEqualTo("cartId", product.cartId)
                .whereEqualTo("productId", productId)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        for (document in querySnapshot.documents) {
                            db.collection("CartItems").document(document.id).delete()
                                .addOnSuccessListener {
                                    Log.d(TAG, "Product removed from cart successfully.")
                                }
                                .addOnFailureListener { e ->
                                    Log.e(TAG, "Error removing product from cart", e)
                                }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error finding product in cart", e)
                }
        }
    }

    // apagar carrinho
    fun deleteCart(cartId: String) {
        val db = Firebase.firestore

        db.collection("Carts")
            .document(cartId)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Cart deleted successfully!")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error deleting cart", e)
            }
    }
}