package com.example.exerciciomaquinacalculadora.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.width

@Composable
fun CalculatorButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(80.dp)
            .height(60.dp)
    ) {
        Text(label)
    }
    Spacer(modifier = Modifier.width(3.dp))
}