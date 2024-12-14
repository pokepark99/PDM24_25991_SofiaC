package com.example.firebasecompras.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.firebasecompras.MainActivity
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    //registrar utilizador utilizando firebase
    fun registerUserFirebase(
        email: String,
        password: String,
        name: String,
        navController: NavHostController,
        mainActivity: MainActivity
    ) {
        viewModelScope.launch { //usa a funcao na MainActivity
            mainActivity.registerUserFirebase(email, password, name, navController)
        }
    }
}