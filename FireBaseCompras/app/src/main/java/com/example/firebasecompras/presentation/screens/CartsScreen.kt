package com.example.firebasecompras.presentation.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.firebasecompras.data.domain.model.CartItems
import com.example.firebasecompras.data.domain.model.Carts
import com.example.firebasecompras.presentation.viewModels.CartsViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.util.Date

@Composable
fun CartsScreen(navController: NavHostController) {
    //estado dos carrinhos
    val carts = remember { mutableStateOf<List<Pair<String, Carts>>>(emptyList()) }

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    val showDialog = remember { mutableStateOf(false) }
    val newCartName = remember { mutableStateOf("") }

    // Buscar carrinhos (do utilizador e partilhados com o utilizador)
    LaunchedEffect(userId) {
        if (userId != null) {
            val db = Firebase.firestore

            db.collection("Carts")
                .whereEqualTo("ownerId", userId)
                .whereEqualTo("isBought", false) //onde isBought e falso -- o carrinho ainda nao foi pagado
                .addSnapshotListener { ownedCartsSnapshot, e ->
                    if (e != null) {
                        Log.e(TAG, "Listen for owned carts failed.", e)
                        return@addSnapshotListener //se houver mudancas nos carrinhos do utilizador
                    }

                    val ownedCarts = ownedCartsSnapshot?.documents?.map { it.id to it.toObject(Carts::class.java)!! } ?: emptyList()

                    db.collection("Carts")
                        .whereArrayContains("sharedWith", userId)
                        .whereEqualTo("isBought", false)
                        .addSnapshotListener { sharedCartsSnapshot, sharedError ->
                            if (sharedError != null) {
                                Log.e(TAG, "Listen for shared carts failed.", sharedError)
                                return@addSnapshotListener //se houver mudancas nos carrinhos partilhardos com o utilizador
                            }

                            val sharedCarts = sharedCartsSnapshot?.documents?.map { it.id to it.toObject(Carts::class.java)!!} ?: emptyList()

                            // carrinhos do utilizador e partilhados com ele
                            carts.value = ownedCarts + sharedCarts
                        }
                }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF9C4))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //LogOut
            Button(onClick = { navController.navigate("inicio") }) {
                Text("LogOut")
            }
            //Criar um novo carrinho
            Button(
                onClick = {
                    showDialog.value = true // para escrever o nome do novo carrinho
                }
            ) {
                Text("Create New Cart")
            }
        }

        LazyColumn {
            items(carts.value) { (cartId, cart) ->
                if (userId != null) {
                    CartCard(cart = cart, cartId = cartId, currentUserId = userId, navController)
                }
            }
        }
    }

    // pop-up para um novo carrinho
    if (showDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("New Cart") },
            text = {
                Column {
                    Text("Enter a name for the new cart:")
                    androidx.compose.material3.TextField(
                        value = newCartName.value,
                        onValueChange = { newCartName.value = it },
                        placeholder = { Text("Cart Name") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        createNewCart(userId ?: "", newCartName.value)
                        showDialog.value = false
                        newCartName.value = ""
                    }
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Novo Carrinho no firestore
fun createNewCart(userId: String, cartName: String) {
    val db = Firebase.firestore
    val newCart = hashMapOf(
        "name" to (cartName.ifEmpty { "Unnamed Cart" }),
        "createdAt" to Date(),
        "ownerId" to userId,
        "sharedWith" to emptyList<String>(),
        "isBought" to false
    )

    db.collection("Carts")
        .add(newCart)
        .addOnSuccessListener {
            Log.d(TAG, "New cart created successfully!")
        }
        .addOnFailureListener { e ->
            Log.e(TAG, "Error creating new cart", e)
        }
}

//seccao para cada carrinho
@Composable
fun CartCard(cart: Carts, cartId: String, currentUserId: String, navController: NavHostController) {
    val cartsViewModel: CartsViewModel = viewModel()

    val products = remember { mutableStateOf<List<CartItems>>(emptyList()) }
    val productDetails = remember { mutableStateOf<Map<String, Pair<String, Double>>>(emptyMap()) }
    val isSharedCart = cart.ownerId != currentUserId
    val showShareDialog = remember { mutableStateOf(false) }
    val shareWithEmail = remember { mutableStateOf("") }
    val showRenameDialog = remember { mutableStateOf(false) }
    val newCartName = remember { mutableStateOf(cart.name ?: "") }

    // Buscar itens to carrinho, alterar se houver mudanças
    LaunchedEffect(cartId) {
        val db = Firebase.firestore
        db.collection("CartItems")
            .whereEqualTo("cartId", cartId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e(TAG, "Error listening for CartItems updates", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val cartItems = snapshot.toObjects(CartItems::class.java)
                    products.value = cartItems

                    // buscar informacao de cada item
                    cartItems.forEach { cartItem ->
                        cartItem.productId?.let { productId ->
                            db.collection("Products")
                                .document(productId)
                                .get()
                                .addOnSuccessListener { document ->
                                    val nome = document.getString("nome") ?: "Unknown Product"
                                    val price = document.getDouble("price") ?: 0.0
                                    productDetails.value += (productId to (nome to price))
                                }
                                .addOnFailureListener { e ->
                                    Log.e(TAG, "Error fetching product details for $productId", e)
                                }
                        }
                    }
                }
            }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = cart.name ?: "Unnamed Cart",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    // se o carrinho é partilhado com o utilizador
                    if (isSharedCart) {
                        Text(
                            text = "Shared",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
                //se o carrinho é do utilizador
                if (!isSharedCart) {
                    Row {
                        //renomear o carrinho
                        Button(onClick = { showRenameDialog.value = true }) {
                            Text("Rename")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        //partilhar o carrinho
                        Button(onClick = { showShareDialog.value = true }) {
                            Text("Share Cart")
                        }
                    }
                }
            }

            //se o carrinho não tem itens
            if (products.value.isEmpty()) {
                Text(
                    text = "No items in this cart.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            } else {
                products.value.forEach { product ->
                    val details = productDetails.value[product.productId]
                    ProductRow(
                        product = product,
                        productName = details?.first ?: "Loading...",
                        productPrice = details?.second ?: 0.0,
                        isOwnedByUser = !isSharedCart //se o carrinho é partilhado com o utilizador atual ou nao
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly // Space buttons evenly
            ) {
                // Delete Button
                Button(
                    onClick = {
                        cartsViewModel.deleteCart(cartId)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Red button
                ) {
                    Text("Delete", color = Color.White)
                }

                // Buy Button
                Button(
                    onClick = {
                        navController.navigate("pay/${cartId}")
                    }
                ) {
                    Text("Buy")
                }
            }
        }
    }

    // pop-up para partilhar o carrinho (atraves do email)
    if (showShareDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showShareDialog.value = false },
            title = { Text("Share Cart") },
            text = {
                Column {
                    Text("Enter the email of the user to share with:")
                    androidx.compose.material3.TextField(
                        value = shareWithEmail.value,
                        onValueChange = { shareWithEmail.value = it },
                        placeholder = { Text("User Email") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        cartsViewModel.shareCart(cartId, shareWithEmail.value)
                        showShareDialog.value = false
                        shareWithEmail.value = ""
                    }
                ) {
                    Text("Share")
                }
            },
            dismissButton = {
                Button(onClick = { showShareDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    // pop-up para renomiar o carrinho
    if (showRenameDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showRenameDialog.value = false },
            title = { Text("Rename Cart") },
            text = {
                Column {
                    Text("Enter the new name for the cart:")
                    androidx.compose.material3.TextField(
                        value = newCartName.value,
                        onValueChange = { newCartName.value = it },
                        placeholder = { Text("New Cart Name") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        cartsViewModel.renameCart(cartId, newCartName.value) // renomear carrinho
                        showRenameDialog.value = false
                    }
                ) {
                    Text("Rename")
                }
            },
            dismissButton = {
                Button(onClick = { showRenameDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// listar os detalhes dos produtos
@Composable
fun ProductRow(product: CartItems, productName: String, productPrice: Double, isOwnedByUser: Boolean) {
    val cartsViewModel : CartsViewModel = viewModel()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = productName,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Quantity: ${product.quantity}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = "€ ${"%.2f".format(productPrice)}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 8.dp)
            )

            // apenas tem a opcao de remover um produto de um carrinho se pertencer ao utilizador
            if (isOwnedByUser) {
                Button(
                    onClick = { cartsViewModel.removeProductFromCart(product) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Red
                    ),
                    modifier = Modifier
                ) {
                    Text("Remove")
                }
            }
        }
    }
}