package com.example.listadelacompra.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.listadelacompra.databinding.ActivityAuthBinding
import com.example.listadelacompra.ui.AuthViewModel
import com.example.listadelacompra.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AuthActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAuthBinding

    private val db = Firebase.firestore

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        if (supportActionBar != null) {
            supportActionBar?.hide()
        }

        //Splash
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        mBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        splashScreen.setKeepOnScreenCondition{
            false
        }

        //Setup
        setup()
    }

    private fun setup() {

        authViewModel.login.observe(this) {result->
            if (result.isSuccessful) {
                createUser(FirebaseAuth.getInstance().currentUser?.email.toString() + "#" + "Lista de la compra") //conseguir arreglar esto
                showMain()
            } else {
                showAlert()
            }

        }

        mBinding.btnSignUp.setOnClickListener {
            if (mBinding.etEmail.text.isNotEmpty() && mBinding.etPassword.text?.isNotEmpty() == true) {
                authViewModel.register(email = mBinding.etEmail.text.toString(), password = mBinding.etPassword.text.toString())

            }
        }

        mBinding.btnLogin.setOnClickListener {
            if (mBinding.etEmail.text.isNotEmpty() && mBinding.etPassword.text?.isNotEmpty() == true) {
                authViewModel.login(email = mBinding.etEmail.text.toString(), password = mBinding.etPassword.text.toString())

            }
        }
    }

    private fun createUser(email: String) {
        val element = hashMapOf(
            "Element" to "pepinillos",
            "Complete" to false
        )
        Log.i("Ignacio", email)
        Log.i("Ignacio", element.toString())
        db.collection(email)
            //.document("Lista de la compra")
            //.set(element)
            .add(element)
            .addOnSuccessListener {
                Log.i("Ignacio", "DocumentSnapshot added with ID:")
            }
            .addOnFailureListener {
                Log.i("Ignacio", "Error adding document")
            }
    }

    private fun showAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha profucido un error autenticando al ususario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}