package com.example.sortit

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sortit.databinding.ActivityProfileBinding
import com.example.sortit.settingScreens.SettingsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        var name = currentUser?.displayName
        val email = currentUser?.email ?: "Correo no disponible"

//        binding.username.text = ""
//        binding.username.setVisibility(View.INVISIBLE)

        binding.username.text = name
        binding.email.text = email

        if (name.isNullOrEmpty()) {
            val userId = currentUser?.uid
            if (userId != null) {
                val database = FirebaseDatabase.getInstance().reference
                database.child("users").child(userId).child("name").get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val fetchedName = task.result?.getValue(String::class.java)
                        name = fetchedName?.takeIf { it.isNotEmpty() } ?: "Cambiar nombre"
                    } else {
                        name = "Cambiar nombre"  // En caso de error, usar valor por defecto
                    }

                    // Mostrar el nombre correctamente recuperado
                    binding.username.text = name
                    binding.username.setVisibility(View.VISIBLE)
                }
            }
        } else {
            binding.username.text = name
            binding.username.setVisibility(View.VISIBLE)
        }



        binding.username.setOnClickListener {
            showChangeNameDialog()
        }

        binding.iconBuscar.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.iconSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.navigationBar.homeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.navigationBar.profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun showChangeNameDialog() {
        val currentName = binding.username.text.toString()

        val editText = EditText(this).apply {
            setText(currentName)
            hint = "Introduce tu nuevo nombre"
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Cambiar nombre")
            .setView(editText)
            .setPositiveButton("Guardar") { _, _ ->
                val newName = editText.text.toString()

                if (newName.isNotEmpty()) {
                    updateUserName(newName)
                } else {
                    Log.w(TAG, "El nombre no puede estar vacío")
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }

    private fun updateUserName(newName: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            // Actualizar en Firebase Authentication
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()

            currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = currentUser.uid
                        val database = FirebaseDatabase.getInstance().reference
                        val userUpdates = mapOf("name" to newName)
                        database.child("users").child(userId).updateChildren(userUpdates)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    binding.username.text = newName
                                    Log.d(TAG, "Nombre actualizado")
                                } else {
                                    Log.w(TAG, "Error al actualizar el nombre")
                                }
                            }
                        Log.d(TAG, "Nombre actualizado ")
                    } else {
                        Log.w(TAG, "Error al actualizar")
                    }
                }
        }
    }
}
