package com.example.guardsprotectionapp.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun TextInputLayout.setErrorText(text: String?){
    text?.let {
        error = text
    }
}
