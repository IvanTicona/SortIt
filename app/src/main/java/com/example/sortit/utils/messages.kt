package com.example.sortit.utils

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sortit.R
import com.example.sortit.databinding.ErrorMessageBoxBinding
import com.example.sortit.databinding.SuccessMessageBoxBinding
import com.example.sortit.databinding.WarningMessageBoxBinding

fun ConstraintLayout.showTemporaryErrorMessage(
    inflater: LayoutInflater,
    message: String,
    specific: String,
    duration: Long = 3000
) {
    if (this.findViewById<View>(R.id.errorAlert) != null) return

    val binding = ErrorMessageBoxBinding.inflate(inflater, this, false)

    binding.mainMessage.text = message
    binding.specificMessage.text = specific

    binding.root.id = R.id.errorAlert

    val layoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.MATCH_PARENT,
        dpToPx(50, this.resources.displayMetrics.density)
    ).apply {
        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        topMargin = dpToPx(10, this@showTemporaryErrorMessage.resources.displayMetrics.density)
    }
    binding.root.layoutParams = layoutParams

    this.addView(binding.root)

    Handler(Looper.getMainLooper()).postDelayed({
        this.removeView(binding.root)
    }, duration)
}

fun ConstraintLayout.showTemporaryWarningMessage(
    inflater: LayoutInflater,
    message: String,
    specific: String,
    duration: Long = 3000
) {
    if (this.findViewById<View>(R.id.errorAlert) != null) return

    val binding = WarningMessageBoxBinding.inflate(inflater, this, false)

    binding.mainMessage.text = message
    binding.specificMessage.text = specific

    binding.root.id = R.id.errorAlert

    val layoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.MATCH_PARENT,
        dpToPx(50, this.resources.displayMetrics.density)
    ).apply {
        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        topMargin = dpToPx(10, this@showTemporaryWarningMessage.resources.displayMetrics.density)
    }
    binding.root.layoutParams = layoutParams

    this.addView(binding.root)

    Handler(Looper.getMainLooper()).postDelayed({
        this.removeView(binding.root)
    }, duration)
}

fun ConstraintLayout.showTemporarySuccessMessage(
    inflater: LayoutInflater,
    message: String,
    specific: String,
    duration: Long = 3000
) {
    if (this.findViewById<View>(R.id.errorAlert) != null) return

    val binding = SuccessMessageBoxBinding.inflate(inflater, this, false)

    binding.mainMessage.text = message
    binding.specificMessage.text = specific

    binding.root.id = R.id.errorAlert

    val layoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.MATCH_PARENT,
        dpToPx(50, this.resources.displayMetrics.density)
    ).apply {
        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        topMargin = dpToPx(10, this@showTemporarySuccessMessage.resources.displayMetrics.density)
    }
    binding.root.layoutParams = layoutParams

    this.addView(binding.root)

    Handler(Looper.getMainLooper()).postDelayed({
        this.removeView(binding.root)
    }, duration)
}