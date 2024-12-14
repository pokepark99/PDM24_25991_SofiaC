package com.example.firebasecompras.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.firebasecompras.MainActivity
import com.example.firebasecompras.presentation.viewModels.LoginViewModel

@Composable
fun LoginScreen(navController: NavHostController){
    val loginViewModel: LoginViewModel = viewModel()
    val mainActivity = LocalContext.current as MainActivity

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //TextField email
        TextField(
            value = email.value,
            label = { Text("Email") },
            onValueChange = { email.value = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        //TextField password
        TextField(
            value = password.value,
            label = { Text("Password") },
            onValueChange = { password.value = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        //Login
        Button(onClick = {
            loginViewModel.signInUserFirebase(email.value, password.value, navController, mainActivity)
        }) {
            Text("Login")
        }
    }
}