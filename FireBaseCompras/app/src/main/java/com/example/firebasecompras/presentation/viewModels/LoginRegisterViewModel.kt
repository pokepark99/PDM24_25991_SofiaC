package com.example.firebasecompras.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class LoginRegisterViewModel : ViewModel() {

    // Navegar para a pagina de login
    fun navigateToLogin(navController: NavHostController) {
        navController.navigate("login")
    }

    // Navegar para a pagina de sign up
    fun navigateToSignUp(navController: NavHostController) {
        navController.navigate("signup")
    }
}