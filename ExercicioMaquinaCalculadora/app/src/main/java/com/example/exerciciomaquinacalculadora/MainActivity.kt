package com.example.exerciciomaquinacalculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.exerciciomaquinacalculadora.ui.CalculatorUI
import com.example.exerciciomaquinacalculadora.ui.theme.ExercicioMaquinaCalculadoraTheme
import com.example.exerciciomaquinacalculadora.viewmodel.CalculatorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExercicioMaquinaCalculadoraTheme {
                val viewModel = CalculatorViewModel()
                CalculatorUI(viewModel)
            }
        }
    }
}