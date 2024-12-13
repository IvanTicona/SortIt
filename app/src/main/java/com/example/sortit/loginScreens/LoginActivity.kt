package com.example.sortit.loginScreens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sortit.HomeActivity
import com.example.sortit.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        auth = Firebase.auth

        val view = binding.root
        setContentView(view)

        binding.buttonLogCorreo.setOnClickListener {
            val intentLoginEmail = Intent(this, LoginEmailActivity::class.java)
            startActivity(intentLoginEmail)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intentHomeScreen = Intent(this, HomeActivity::class.java)
//             startActivity(intentHomeScreen)
        }
    }
}