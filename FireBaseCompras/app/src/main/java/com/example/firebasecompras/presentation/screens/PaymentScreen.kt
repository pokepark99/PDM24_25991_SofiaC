package com.example.firebasecompras.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.firebasecompras.presentation.viewModels.PaymentViewModel

@Composable
fun PaymentScreen (navController: NavHostController, cartId: String){
    val context = LocalContext.current
    val viewModel = PaymentViewModel()

    var selectedPaymentMethod by remember { mutableStateOf("") }
    val paymentMethods = listOf("MB Way", "Credit Card", "PayPal") // métodos de pagamento

    var mbWayNumber by remember { mutableStateOf("") }
    var mbWayConfirmationCode by remember { mutableStateOf("") }
    var creditCardNumber by remember { mutableStateOf("") }
    var creditCardExpiration by remember { mutableStateOf("") }
    var creditCardCVV by remember { mutableStateOf("") }
    var paypalEmail by remember { mutableStateOf("") }
    var paypalPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        )
        {
            Spacer(modifier = Modifier.height(40.dp))

            //selecionar metodo de pagamento
            Text(
                text = "Select Payment Method",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            //dropdown dos metodos
            DropdownMenuWithSelection(
                options = paymentMethods,
                selectedOption = selectedPaymentMethod,
                onOptionSelected = { selectedPaymentMethod = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // campos diferentes com base no metodo de pagamento
            when (selectedPaymentMethod) {
                "MB Way" -> {
                    TextField(
                        value = mbWayNumber,
                        onValueChange = { if (it.length <= 9 && it.all { char -> char.isDigit() }) mbWayNumber = it },
                        label = { Text("MB Way Number (9 digits)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = mbWayConfirmationCode,
                        onValueChange = { if (it.length <= 6 && it.all { char -> char.isDigit() }) mbWayConfirmationCode = it },
                        label = { Text("Confirmation Code (6 digits)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                "Credit Card" -> {
                    TextField(
                        value = creditCardNumber,
                        onValueChange = { if (it.length <= 16 && it.all { char -> char.isDigit() }) creditCardNumber = it },
                        label = { Text("Card Number (16 digits)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = creditCardExpiration,
                        onValueChange = { input ->
                            val sanitizedInput = input.replace(Regex("\\D"), "")
                            val formattedInput = when {
                                sanitizedInput.isEmpty() -> ""
                                sanitizedInput.length == 1 -> sanitizedInput
                                sanitizedInput.length == 2 -> {
                                    // verifica que o mes e valido
                                    if (sanitizedInput.toInt() in 1..12) sanitizedInput else sanitizedInput.substring(0, 1)
                                }
                                sanitizedInput.length in 3..4 -> {
                                    val month = sanitizedInput.substring(0, 2)
                                    val year = sanitizedInput.substring(2)
                                    if (month.toInt() in 1..12) "$month/$year" else month.substring(0, 1)
                                }
                                sanitizedInput.length > 4 -> {
                                    val month = sanitizedInput.substring(0, 2)
                                    val year = sanitizedInput.substring(2, 4)
                                    if (month.toInt() in 1..12) "$month/$year" else month.substring(0, 1)
                                }
                                else -> sanitizedInput
                            }
                            creditCardExpiration = formattedInput
                        },
                        label = { Text("Expiration Date (MM/YY)") },
                        placeholder = { Text("MM/YY") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = creditCardCVV,
                        onValueChange = { if (it.length <= 3 && it.all { char -> char.isDigit() }) creditCardCVV = it },
                        label = { Text("CVV (3 digits)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                "PayPal" -> {
                    TextField(
                        value = paypalEmail,
                        onValueChange = { paypalEmail = it },
                        label = { Text("PayPal Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = paypalPassword,
                        onValueChange = { if (it.length <= 20) paypalPassword = it },
                        label = { Text("Password (min. 6 characters)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // botao para cancelar
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Cancel")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    // se nao foi selecionado metodo de pagamento
                    if (selectedPaymentMethod.isEmpty()) {
                        Toast.makeText(context, "Please select a payment method.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // campos mandatorios
                    when (selectedPaymentMethod) {
                        "MB Way" -> {
                            if (mbWayNumber.length != 9 || mbWayConfirmationCode.length != 6) {
                                Toast.makeText(
                                    context,
                                    "Invalid MB Way Number or Confirmation Code.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                        }
                        "Credit Card" -> {
                            if (creditCardNumber.length != 16 || !creditCardExpiration.matches(Regex("^\\d{2}/\\d{2}$")) || creditCardCVV.length != 3) {
                                Toast.makeText(
                                    context,
                                    "Complete and valid credit card details are required.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                        }
                        "PayPal" -> {
                            if (!paypalEmail.contains("@") || paypalPassword.length < 6) {
                                Toast.makeText(
                                    context,
                                    "Valid PayPal Email and Password are required.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                        }
                    }
                    viewModel.confirmPurchase(context, cartId, navController)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Confirm")
            }
        }
    }
}

// dropdown dos metodos de pagamento
@Composable
fun DropdownMenuWithSelection(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedOption.ifEmpty { "Select a Payment Method" },
                    style = MaterialTheme.typography.bodyLarge
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { method ->
                DropdownMenuItem(
                    text = { Text(method) },
                    onClick = {
                        onOptionSelected(method)
                        expanded = false
                    }
                )
            }
        }
    }
}
