package com.example.firebasecompras

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import com.example.firebasecompras.data.domain.model.Products
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        val db = Firebase.firestore

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //AddProducts(db) //para adicionar produtos a firebase
            AppNavigation()
        }
    }

    fun registerUserFirebase(email: String, password: String, name: String, navController: NavHostController) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Get the current user
                    val user = auth.currentUser
                    user?.let {
                        val uid = it.uid // Get the user's UID

                        // Save the name to Firestore
                        val db = Firebase.firestore
                        val userMap = hashMapOf(
                            "name" to name,
                            "sharedCarts" to emptyList<String>() // Initialize sharedCarts as an empty list
                        )

                        db.collection("users").document(uid)
                            .set(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Registration and Firestore Save Successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate("inicio")

                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "Error saving to Firestore", e)
                                Toast.makeText(
                                    this,
                                    "Registration Successful, but Firestore Save Failed: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                } else {
                    // Handle registration failure
                    Toast.makeText(
                        this,
                        "Registration Failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun signInUserFirebase(email: String, password: String, navController: NavHostController) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("mainScreen")
                } else {
                    Toast.makeText(
                        this,
                        "Login Failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

//utilizado para adicionar produtos a firebase
/*
fun AddProducts(db : FirebaseFirestore){
    val fakeProducts = listOf(
        Products("A high-quality leather jacket", "Leather Jacket", 99.99, 15),
        Products("A comfortable cotton t-shirt", "Cotton T-shirt", 19.99, 50),
        Products("Bluetooth headphones with noise-cancellation", "Bluetooth Headphones", 149.99, 30),
        Products("A smartwatch with heart rate monitor", "Smartwatch", 249.99, 40),
        Products("Durable hiking boots", "Hiking Boots", 89.99, 25),
        Products("Classic denim jeans", "Denim Jeans", 49.99, 60),
        Products("Stylish sunglasses for summer", "Sunglasses", 29.99, 100),
        Products("Premium wireless mouse", "Wireless Mouse", 39.99, 80),
        Products("Gaming mouse pad with RGB lighting", "Gaming Mouse Pad", 19.99, 120),
        Products("Portable power bank for charging on the go", "Power Bank", 34.99, 45)
    )

    // adicionar cada produto na lista ao firestore
    for (product in fakeProducts) {
        val productData = hashMapOf(
            "description" to product.description,
            "nome" to product.nome,
            "price" to product.price,
            "stock" to product.stock
        )

        db.collection("Products")
            .add(productData)
            .addOnSuccessListener { documentReference ->
                Log.d("AddFakeProducts", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("AddFakeProducts", "Error adding document", e)
            }
    }
}
 */