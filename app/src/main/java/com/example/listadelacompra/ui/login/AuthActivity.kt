package com.example.listadelacompra.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listadelacompra.R
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.listadelacompra.databinding.ActivityAuthBinding
import com.example.listadelacompra.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class AuthActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        //Splash
        Thread.sleep(2000) //Hack
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        mBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //Setup
        setup()
    }

    private fun setup() {
        title = "Autenticación"

        mBinding.btnSignUp.setOnClickListener {
            if (mBinding.etEmail.text.isNotEmpty() && mBinding.etPassword.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(mBinding.etEmail.text.toString(),
                        mBinding.etPassword.text.toString()).addOnCompleteListener {

                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        mBinding.btnLogIn.setOnClickListener {
            if (mBinding.etEmail.text.isNotEmpty() && mBinding.etPassword.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(mBinding.etEmail.text.toString(),
                        mBinding.etPassword.text.toString()).addOnCompleteListener {

                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }
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