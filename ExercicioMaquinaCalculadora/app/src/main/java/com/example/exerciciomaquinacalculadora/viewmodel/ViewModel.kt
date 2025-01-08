package com.example.exerciciomaquinacalculadora.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.exerciciomaquinacalculadora.models.CalculatorBrain

class CalculatorViewModel : ViewModel() {
    private val calculatorBrain = CalculatorBrain()

    val apr = mutableStateOf("")
    val valorAnterior = mutableStateOf("")
    val operacao = mutableStateOf("")

    fun handleButtonPress(buttonLabel: String) {
        when (buttonLabel) {
            "+/-" -> apr.value = calculatorBrain.toggleSign(apr.value)
            "CE" -> resetCalculator()
            "%" -> apr.value = calculatorBrain.applyPercentage(apr.value)
            "sqrt" -> apr.value = calculatorBrain.applySquareRoot(apr.value)
            "+", "-", "/", "x" -> {
                if (valorAnterior.value.isEmpty()) {
                    valorAnterior.value = apr.value
                } else {
                    valorAnterior.value = calculatorBrain.calculateResult(
                        valorAnterior.value,
                        operacao.value,
                        apr.value
                    )
                }
                apr.value = ""
                operacao.value = buttonLabel
            }
            "=" -> {
                apr.value = calculatorBrain.calculateResult(
                    valorAnterior.value,
                    operacao.value,
                    apr.value
                )
                valorAnterior.value = ""
                operacao.value = ""
            }
            else -> {
                if (apr.value.isEmpty() && buttonLabel == ".") {
                    apr.value = "0."
                } else if (buttonLabel == "." && !apr.value.contains(".")) {
                    apr.value += buttonLabel
                } else if (buttonLabel != "0" || apr.value.isNotEmpty()) {
                    apr.value += buttonLabel
                }
            }
        }
    }

    fun resetCalculator() {
        apr.value = ""
        valorAnterior.value = ""
        operacao.value = ""
    }
}

