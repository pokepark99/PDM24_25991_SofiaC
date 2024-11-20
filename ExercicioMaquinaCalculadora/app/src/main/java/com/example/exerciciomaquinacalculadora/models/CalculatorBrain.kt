package com.example.exerciciomaquinacalculadora.models

import kotlin.math.sqrt

class CalculatorBrain {
    //Calcula o resultado de uma operacao
    fun calculateResult(valorAnterior: String, operacao: String, apr: String): String {
        if (valorAnterior.isEmpty() || operacao.isEmpty() || apr.isEmpty()) {
            return apr
        }

        //converte para numero
        val num1 = valorAnterior.toDouble()
        val num2 = apr.toDouble()

        //executa a operacao
        return when (operacao) {
            "+" -> (num1 + num2).toString()
            "-" -> (num1 - num2).toString()
            "/" -> if(num2 == 0.0)"Error" else (num1 / num2).toString()
            "x" -> (num1 * num2).toString()
            else -> apr //se o operador for invalido
        }
    }

    //sinal de negativo
    fun toggleSign(value: String): String {
        return if (value.startsWith("-")) value.drop(1) else "-$value"
    }
    //percentagem
    fun applyPercentage(value: String, isPercentage: Boolean): String {
        return if (isPercentage) (value.toDouble() * 100).toString() else (value.toDouble() / 100).toString()
    }
    //sqrt
    fun applySquareRoot(value: String): String {
        return sqrt(value.toDouble()).toString()
    }
}