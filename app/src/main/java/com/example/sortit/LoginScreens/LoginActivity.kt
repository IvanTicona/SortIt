package com.example.sortit.LoginScreens

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sortit.HomeScreenActivity
import com.example.sortit.R
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
            val intentHomeScreen = Intent(this, HomeScreenActivity::class.java)
            // startActivity(intentHomeScreen)
        }
    }
}