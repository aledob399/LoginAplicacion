package com.example.loginaplicacion

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import java.nio.BufferUnderflowException

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bundle:Bundle?=intent.extras
        val email:String?=bundle?.getString("email")
        val provider:String?=bundle?.getString("provider")
        setup(email?:"",provider?:"")
    }
    @SuppressLint("WrongViewCast")
    private fun setup(email:String, provider:String){
        val emailEditText=findViewById<EditText>(R.id.editTextRegisteredEmail)
        val logOutButton=findViewById<Button>(R.id.btnLogOut)


        logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

    }
}