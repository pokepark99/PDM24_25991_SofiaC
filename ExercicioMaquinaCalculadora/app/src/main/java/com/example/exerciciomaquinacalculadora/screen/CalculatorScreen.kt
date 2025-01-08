package com.example.exerciciomaquinacalculadora.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.exerciciomaquinacalculadora.ui.CalculatorButton
import com.example.exerciciomaquinacalculadora.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen() {
    val viewModel: CalculatorViewModel = viewModel()
    val apr by viewModel.apr
    val buttonLabels = listOf(
        listOf("sqrt", "%", "+/-", "CE"),
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "x"),
        listOf("1", "2", "3", "-"),
        listOf("0", ".", "=", "+")
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = apr,
                onValueChange = {},
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Black,
                    focusedContainerColor = Color.Black,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f)
        ) {
            buttonLabels.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    row.forEach { label ->
                        CalculatorButton(label = label) {
                            viewModel.handleButtonPress(label)
                        }
                    }
                }
            }
        }
    }
}