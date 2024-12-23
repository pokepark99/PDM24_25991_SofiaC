package com.example.firebasecompras.presentation.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore

class PaymentViewModel : ViewModel(){
    private val db = FirebaseFirestore.getInstance()

    fun confirmPurchase(context: Context, cartId: String, navController: NavHostController) {
        db.collection("Carts")
            .document(cartId)
            .update("isBought", true)
            .addOnSuccessListener {
                Toast.makeText(context, "Purchase successful for cart $cartId!", Toast.LENGTH_LONG).show()
                navController.popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to complete purchase for cart $cartId.", Toast.LENGTH_LONG).show()
            }
    }
}