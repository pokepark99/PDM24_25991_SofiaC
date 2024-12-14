package com.example.firebasecompras.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.firebasecompras.data.domain.model.Products
import com.example.firebasecompras.presentation.viewModels.MainViewModel

@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: MainViewModel = viewModel()

    // produtos e carrinho
    val products = viewModel.products.collectAsState()
    val carts = viewModel.carts.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF9C4))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //botao para logout
            Button(onClick = { navController.navigate("inicio") }) {
                Text("LogOut")
            }
            //butao para ir para a pagina dos carrinho
            Button(onClick = { navController.navigate("carts") }) {
                Text("See Carts")
            }
        }

        // Lista de produtos
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(products.value) { product ->
                ProductCard(
                    product = product,
                    carts = carts.value,
                    onAddToCart = { cartId, productId, quantity ->
                        viewModel.addToCart(cartId, productId, quantity)
                    },
                    fetchProductId = { productName, onSuccess ->
                        viewModel.fetchProductIdByName(productName, onSuccess)
                    }
                )
            }
        }
    }
}

//cada seccao de um produto
@Composable
fun ProductCard(
    product: Products,
    carts: List<Pair<String, String>>,
    onAddToCart: (String, String, Int) -> Unit,
    fetchProductId: (String, (String) -> Unit) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.nome ?: "Unknown Product", //valor default se nao conseguir encontrar o nome
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.description ?: "No description available",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "Stock: ${product.stock}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "€ ${"%.2f".format(product.price)}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Button(onClick = { showDialog.value = true }) {
                    Text("Add to Cart")
                }
            }
        }
    }

    if (showDialog.value) {
        AddToCartDialog(
            product = product,
            carts = carts,
            onDismiss = { showDialog.value = false },
            onAddToCart = onAddToCart,
            fetchProductId = fetchProductId
        )
    }
}

//pop-up para adicionar um produto ao carrinho
@Composable
fun AddToCartDialog(
    product: Products,
    carts: List<Pair<String, String>>,
    onDismiss: () -> Unit,
    onAddToCart: (String, String, Int) -> Unit, //para a funcao addToCart
    fetchProductId: (String, (String) -> Unit) -> Unit //para a funcao fetchProductIdByName
) {
    val quantity = remember { mutableStateOf(1) }
    val selectedCartId = remember { mutableStateOf<String?>(null) }
    val selectedCartName = remember { mutableStateOf<String?>(null) }
    val isDropdownExpanded = remember { mutableStateOf(false) }
    val productId = remember { mutableStateOf<String?>(null) }

    // Se ainda nao tem o product ID, busca-o
    LaunchedEffect(product) {
        fetchProductId(product.nome ?: "") { id ->
            productId.value = id
        }
    }

    //pop-up
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    //fechar pop-up
                    Button(onClick = onDismiss) {
                        Text("Close")
                    }
                }

                Text(
                    text = "Add ${product.nome} to Cart",
                    style = MaterialTheme.typography.titleLarge
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Selecionar Quantidade (+/-)
                    Text(text = "Quantity:")
                    Button(onClick = { if (quantity.value > 1) quantity.value-- }) {
                        Text("-")
                    }
                    Text("${quantity.value}")
                    Button(onClick = { if (quantity.value < product.stock) quantity.value++ }) {
                        Text("+")
                    }
                }

                // Dropdown para selecionar carrinho
                Text(
                    text = "Select Cart:",
                    modifier = Modifier.padding(top = 16.dp)
                )
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(8.dp)
                            .clickable { isDropdownExpanded.value = true },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedCartName.value ?: "Select a Cart",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "▼",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = isDropdownExpanded.value,
                        onDismissRequest = { isDropdownExpanded.value = false }
                    ) {
                        carts.forEach { cart ->
                            DropdownMenuItem(
                                text = { Text(cart.second) },
                                onClick = {
                                    selectedCartId.value = cart.first // Set cart ID
                                    selectedCartName.value = cart.second // Set cart name
                                    isDropdownExpanded.value = false
                                }
                            )
                        }
                    }
                }

                // Adicionar ao Carrinho
                Button(
                    onClick = {
                        if (selectedCartId.value != null && productId.value != null) {
                            onAddToCart(
                                selectedCartId.value!!,
                                productId.value!!,
                                quantity.value
                            )
                            onDismiss()
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp),
                    enabled = selectedCartId.value != null && productId.value != null
                ) {
                    Text("Add to Cart")
                }
            }
        }
    }
}