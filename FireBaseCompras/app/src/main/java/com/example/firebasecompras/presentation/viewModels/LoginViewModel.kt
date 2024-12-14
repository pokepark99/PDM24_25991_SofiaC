package com.example.firebasecompras.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.firebasecompras.MainActivity
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    //login utilizando firebase
    fun signInUserFirebase(email: String, password: String, navController: NavHostController, mainActivity: MainActivity) {
        viewModelScope.launch { //usa a funcao na MainActivity
            mainActivity.signInUserFirebase(email, password, navController)
        }
    }
}