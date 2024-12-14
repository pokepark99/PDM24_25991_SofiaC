package com.example.firebasecompras.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.firebasecompras.presentation.viewModels.LoginRegisterViewModel

@Composable
fun LoginRegisterScreen(navController: NavHostController) {
    // Get the ViewModel instance
    val loginRegisterViewModel: LoginRegisterViewModel = viewModel()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //login
        Button(onClick = {
            loginRegisterViewModel.navigateToLogin(navController)
        }) {
            Text("Login")
        }
        //sign in
        Button(onClick = {
            loginRegisterViewModel.navigateToSignUp(navController)
        }) {
            Text("SignUp")
        }
    }
}