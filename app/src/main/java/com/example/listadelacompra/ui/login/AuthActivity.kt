package com.example.listadelacompra.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listadelacompra.R
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.listadelacompra.databinding.ActivityAuthBinding
import com.example.listadelacompra.ui.AuthViewModel


class AuthActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAuthBinding

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        //Splash
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        mBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //Setup
        setup()
    }

    private fun setup() {
        title = "Autenticación"

        authViewModel.login.observe(this) {result->
            if (result.isSuccessful) showHome(result.result?.user?.email ?: "", ProviderType.BASIC)
            else showAlert()
        }

        mBinding.btnSignUp.setOnClickListener {
            if (mBinding.etEmail.text.isNotEmpty() && mBinding.etPassword.text.isNotEmpty()) {
                authViewModel.register(email = mBinding.etEmail.text.toString(), password = mBinding.etPassword.text.toString())

            }
        }

        mBinding.btnLogIn.setOnClickListener {
            if (mBinding.etEmail.text.isNotEmpty() && mBinding.etPassword.text.isNotEmpty()) {
                authViewModel.login(email = mBinding.etEmail.text.toString(), password = mBinding.etPassword.text.toString())

            }
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

    private fun showHome(email: String, provider: ProviderType) {

        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

}