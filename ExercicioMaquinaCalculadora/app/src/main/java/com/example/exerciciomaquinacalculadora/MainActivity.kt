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
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            TextField(value = "", label = { Text("calc") }, onValueChange = {})
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {}) {
                Text("7")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text("8")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text("9")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text("/")
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {}) {
                Text("4")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text("5")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text("6")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text("*")
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {}) {
                Text("1")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text("2")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text("3")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text("-")
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {}) {
                Text("0")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text(".")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text("=")
            }
            Spacer(modifier = Modifier.width(3.dp))
            Button(onClick = {}) {
                Text("+")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExercicioMaquinaCalculadoraTheme {
        Calc()
    }
}