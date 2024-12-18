package com.example.sortit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sortit.databinding.ActivitySignUpBinding
import com.example.sortit.loginScreens.LoginEmailActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        binding.SignUpButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.confirmPasswordInput.text.toString()

            if (password == confirmPassword) {
                checkIfEmailExists(email) { emailExists ->
                    if (emailExists) {
                        Toast.makeText(this, "Este correo ya está registrado", Toast.LENGTH_SHORT).show()
                    } else {
                        createAccount(email, password, name)
                    }
                }
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkIfEmailExists(email: String, callback: (Boolean) -> Unit) {
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods
                    callback(signInMethods?.isNotEmpty() == true)
                } else {
                    callback(false)
                }
            }
    }

    private fun createAccount(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveUserData(name, email)

                    Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()

                    val loginIntent = Intent(this, LoginEmailActivity::class.java)
                    startActivity(loginIntent)
                } else {
                    Toast.makeText(this, "Error al crear cuenta: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserData(name: String, email: String) {
        val userId = auth.currentUser?.uid ?: return
        val user = mapOf(
            "name" to name,
            "email" to email
        )

        database.child("users").child(userId).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
