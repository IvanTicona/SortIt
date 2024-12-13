package com.example.sortit.LoginScreens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sortit.HomeScreenActivity
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
//            createAccount(email, password)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intentHomeScreen = Intent(this, HomeScreenActivity::class.java)
//            startActivity(intentHomeScreen)
        }
    }

    // Funcion para pantalla Crear Cuenta
//    fun createAccount(email: String, password: String){
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    println("createUserWithEmail:success")
//                    val user = auth.currentUser
//                    // updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    println("createUserWithEmail:failure")
//                    println(task.exception)
//                    // updateUI(null)
//                }
//            }
//    }

    fun signIn(email: String, password: String){
        // Validaciones
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {
                    println("signInWithEmail:success")
                    val intentHomeScreen = Intent(this, HomeScreenActivity::class.java)
//                    startActivity(intentHomeScreen)
                } else {
                    println("signInWithEmail:failure")
                    println(task.exception)
                }
            }
    }


}