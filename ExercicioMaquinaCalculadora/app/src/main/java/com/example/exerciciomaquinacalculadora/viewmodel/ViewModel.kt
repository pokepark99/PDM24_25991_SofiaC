package com.example.exerciciomaquinacalculadora.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.exerciciomaquinacalculadora.models.CalculatorBrain

class CalculatorViewModel : ViewModel() {
    val apr = mutableStateOf("")
    val valorAnterior = mutableStateOf("")
    val operacao = mutableStateOf("")
    val isPercentage = mutableStateOf(false)

    private val brain = CalculatorBrain()

    fun handleButtonPress(buttonLabel: String) {
        when (buttonLabel) {
            "+/-" -> apr.value = brain.toggleSign(apr.value)
            "CE" -> {
                apr.value = ""
                valorAnterior.value = ""
                operacao.value = ""
                isPercentage.value = false
            }
            "%" -> {
                apr.value = brain.applyPercentage(apr.value, isPercentage.value)
                isPercentage.value = !isPercentage.value
            }
            "sqrt" -> apr.value = brain.applySquareRoot(apr.value)
            "+", "-", "/", "x" -> {
                if (valorAnterior.value.isEmpty()) {
                    valorAnterior.value = apr.value
                } else {
                    valorAnterior.value = brain.calculateResult(valorAnterior.value, operacao.value, apr.value)
                }
                apr.value = ""
                operacao.value = buttonLabel
            }
            "=" -> {
                apr.value = brain.calculateResult(valorAnterior.value, operacao.value, apr.value)
                valorAnterior.value = apr.value
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
}