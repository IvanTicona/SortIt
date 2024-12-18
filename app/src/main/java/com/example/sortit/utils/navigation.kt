package com.example.sortit.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun navigateToScreen(
    context: Context,
    destination: Class<out AppCompatActivity>,
    extraKey: String? = null,
    extraValue: Any? = null
) {
    val intent = Intent(context, destination)
    if (extraKey != null && extraValue != null) {
        when (extraValue) {
            is String -> intent.putExtra(extraKey, extraValue)
            is Int -> intent.putExtra(extraKey, extraValue)
            is Boolean -> intent.putExtra(extraKey, extraValue)
            is Long -> intent.putExtra(extraKey, extraValue)
            else -> {
                throw IllegalArgumentException("Tipo de extraValue no soportado.")
            }
        }
    }
    context.startActivity(intent)
}
