package com.example.loginaplicacion
import android.content.Intent

import android.os.Bundle

import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

enum class ProviderType{
    BASIC
}

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("Message","Integracion de firebase completa")
        analytics.logEvent("InitScreen",bundle)
        setup()
    }
    private fun setup(){
        val logInButton = findViewById<Button>(R.id.btnLogin)
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val signInButton=findViewById<Button>(R.id.btnSignIn)

        logInButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            // Registro exitoso

                            showHome(it.result?.user?.email?:"",ProviderType.BASIC)
                        }else {
                            // Registro fallido
                            showAlert()

                        }
                    }


            }

        }
        signInButton.setOnClickListener {
            val intent= Intent(this,SignInActivity::class.java)
            startActivity(intent)
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
        val intentt = Intent(this, HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider)

        }
        startActivity(intent)
    }
}