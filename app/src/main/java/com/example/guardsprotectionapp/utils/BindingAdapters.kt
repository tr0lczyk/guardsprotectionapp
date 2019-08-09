package com.example.guardsprotectionapp.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.guardsprotectionapp.models.OfferModel
import com.example.guardsprotectionapp.ui.panelfragment.OfferAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun TextInputLayout.setErrorText(text: String?){
    text?.let {
        setErrorText(text)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<OfferModel>?) {
    val adapter = recyclerView.adapter as OfferAdapter
    adapter.submitList(data)
}
