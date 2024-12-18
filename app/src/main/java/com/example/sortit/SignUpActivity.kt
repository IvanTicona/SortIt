package com.example.sortit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sortit.databinding.ActivitySignUpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SignUpButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.confirmPasswordInput.text.toString()

            if (password == confirmPassword) {
                database = FirebaseDatabase.getInstance()
                reference = database.getReference("users")

                val userMap = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "password" to password
                )

                reference.child(name).setValue(userMap).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, CreateProfileActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Error al crear cuenta", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }


    }
}