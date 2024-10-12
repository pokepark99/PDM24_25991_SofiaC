package com.example.exerciciomaquinacalculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
 */

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

    val listaButoes = listOf( //usar val pq isto nao pode ser mudado
        listOf("sqrt", "%", "+/-", "CE"),
        listOf("7", "8", "9", "%"),
        listOf("4", "5", "6", "x"),
        listOf("1", "2", "3", "-"),
        listOf("0", ".", "=", "+")
    )
    val listaOperadors = listOf("/", "*", "-", "+")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            TextField(value = apr.value, label = { Text("") }, onValueChange = {})
        }
        Butoes(listaButoes, apr)
    }
}

@Composable
fun Butoes(listaButoes: List<List<String>>, apr: MutableState<String>){
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
                    } else if(apr.value ==""){
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

fun Resultado(){ //funcao para calcular o resultado

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExercicioMaquinaCalculadoraTheme {
        Calc()
    }
}