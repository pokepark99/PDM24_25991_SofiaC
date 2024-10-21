package com.example.exerciciomaquinacalculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exerciciomaquinacalculadora.ui.theme.ExercicioMaquinaCalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExercicioMaquinaCalculadoraTheme {
                Calc()
            }
        }
    }
}

@Composable
fun Calc() {
    var apr = remember {//valor a ser apresentado na tela
        mutableStateOf("") //empty string
    }
    var valorAnterior = remember {//valor inserido anteriormente
        mutableStateOf("")
    }
    var operacao = remember {//valor do resultado atual
        mutableStateOf("")
    }

    val percentagem = remember{//e percentagem ou nao
        mutableStateOf(false)
    }

    val listaButoes = listOf( //usar val pq isto nao pode ser mudado
        listOf("sqrt", "%", "+/-", "CE"),
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "x"),
        listOf("1", "2", "3", "-"),
        listOf("0", ".", "=", "+")
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.background(Color.Black)) {
        Row {
            TextField(value = apr.value, readOnly = true, label = { Text("") }, onValueChange = {}, colors = OutlinedTextFieldDefaults.colors( unfocusedContainerColor = Color.Black, unfocusedTextColor = Color.White, focusedTextColor = Color.White))
        }
        Butoes(listaButoes, apr, valorAnterior, operacao, percentagem)
    }
}

@Composable
fun Butoes(listaButoes: List<List<String>>, apr: MutableState<String>, valorAnterior: MutableState<String>, operacao: MutableState<String>, percentagem: MutableState<Boolean>){
    for (lista in listaButoes){
        Row(verticalAlignment = Alignment.CenterVertically){
            for(numero in lista){
                Button(onClick={
                    if(numero =="+/-"){
                        if (apr.value.startsWith("-")) {
                            apr.value = apr.value.drop(1)
                        } else {
                            apr.value = "-${apr.value}"
                        }
                    } else if (numero == "CE"){
                        apr.value = ""
                        valorAnterior.value = ""
                        operacao.value = ""
                        percentagem.value = false
                    } else if(numero == "%"){
                        if(percentagem.value){ //se e uma percentagem
                            apr.value = (apr.value.toDouble() * 100).toString()
                        } else {
                            apr.value = (apr.value.toDouble() / 100).toString()
                        }
                        percentagem.value = !percentagem.value //o estado muda
                    }else if (numero == "sqrt"){
                        apr.value = Math.sqrt(apr.value.toDouble()).toString()
                    } else if(numero == "+" || numero == "-" || numero == "/" || numero == "x"){
                        if(valorAnterior.value == ""){
                            valorAnterior.value = apr.value
                        } else {
                            valorAnterior.value = Resultado(valorAnterior.value, operacao.value, apr.value)
                            apr.value = valorAnterior.value
                        }
                        apr.value = ""
                        operacao.value = numero
                    } else if (numero == "="){
                        apr.value = Resultado(valorAnterior.value, operacao.value, apr.value)
                        valorAnterior.value = apr.value
                        operacao.value = ""
                    }else if(apr.value == ""){
                        if(numero =="."){
                            apr.value = "0${apr.value}"
                            apr.value += numero
                        } else if (numero != "0"){
                            apr.value += numero
                        }
                    } else if(numero =="."){
                        if(!(apr.value.contains("."))) { //se nao tem . --> um numero nao pode ter mais que um ponto decimal
                            apr.value += numero
                        }
                    } else {
                        apr.value += numero
                    }
                }){
                    Text(numero)
                }
                Spacer(modifier = Modifier.width(3.dp))
            }
        }
    }
}

fun Resultado(valorAnterior: String, operacao: String, apr: String): String { //funcao para calcular o resultado
    if (valorAnterior.isEmpty() || operacao.isEmpty() || apr.isEmpty()) {
        return apr
    }

    val num1 = valorAnterior.toDouble()
    val num2 = apr.toDouble()
    var resultado = ""

    if (operacao == "+"){
        resultado = (num1 + num2).toString()
    } else if (operacao == "-"){
        resultado = (num1 - num2).toString()
    } else if (operacao == "/"){
        resultado = (num1/num2).toString()
    } else if (operacao == "x"){
        resultado = (num1 * num2).toString()
    }
    return resultado
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExercicioMaquinaCalculadoraTheme {
        Calc()
    }
}