package com.example.loginaplicacion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.initialize

class SignInActivity : AppCompatActivity() {



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val analytics:FirebaseAnalytics=FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("Message","Integracion de firebase completa")
        analytics.logEvent("InitScreen",bundle)
        setup()
    }
    private fun setup(){
        val signInButton = findViewById<Button>(R.id.btnRegister)
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)

        signInButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Registro exitoso
                            showHome(task.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            // Registro fallido
                            val error = task.exception?.message ?: "Error desconocido"
                            Log.e("SignInActivity", "Error en el registro: $error")
                            showAlert()
                        }
                    }



            }

        }
    }
    private fun showAlert(){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error")
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }
    private fun showHome(email:String,provider:ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider)

        }
        startActivity(homeIntent)
    }
}

