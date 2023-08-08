package com.example.listadelacompra.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private var _login = MutableLiveData<Task<AuthResult>>()
    val login: LiveData<Task<AuthResult>> = _login

    fun login(email: String, password: String) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password).addOnCompleteListener() {result ->
                _login.value = result
            }
    }

    fun register(email: String, password: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password).addOnCompleteListener() {result ->
                _login.value = result
            }
    }

}