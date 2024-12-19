package com.example.sortit.loginScreens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sortit.HomeActivity
import com.example.sortit.SearchActivity
import com.example.sortit.databinding.ActivityLoginEmailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginEmailBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        auth = Firebase.auth

        val view = binding.root
        setContentView(view)

        binding.SignInButton.setOnClickListener{
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            signIn(email, password)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intentHomeScreen = Intent(this, HomeActivity::class.java)
            startActivity(intentHomeScreen)
        }
    }

    private fun validateEmail(email: String): Boolean {
        val regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(Regex(regexEmail))
    }

    private fun validatePassword(password: String): Boolean {
        val regexPswd = """^.{7,}$""".toRegex()
        return regexPswd.matches(password)
    }


    fun signIn(email: String, password: String){
        // Validaciones
        if(!validateEmail(email)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return
        }
        if(!validatePassword(password)) {
            Toast.makeText(
                this, "Password must have at least 8 characters," +
                        " one uppercase letter, one lowercase letter, and one special character",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {
                    println("signInWithEmail:success")
                    val intentHomeScreen = Intent(this, HomeActivity::class.java)
                    startActivity(intentHomeScreen)
                } else {
                    println("signInWithEmail:failure")
                    println(task.exception)
                }
            }
    }
}