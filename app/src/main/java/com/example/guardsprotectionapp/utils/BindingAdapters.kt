package com.example.guardsprotectionapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.guardsprotectionapp.models.OfferModel
import com.example.guardsprotectionapp.ui.panelfragment.OfferAdapter
import com.google.android.material.textfield.TextInputLayout
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.icu.util.MeasureUnit.BYTE
import android.util.Base64
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import android.R
import android.R.attr.fragment
import com.bumptech.glide.request.RequestOptions






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

@BindingAdapter("loadBitmap")
fun ImageView.loadNewImage(link: String?){
    link?.let {
        val decodedString = Base64.decode(link, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        val byteArrayOutputStream = ByteArrayOutputStream()
        decodedByte.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
        val byte: ByteArray = byteArrayOutputStream.toByteArray()
        val bitmap2 = BitmapFactory.decodeByteArray(byte,0,byte.size)
        this.setImageBitmap(bitmap2)
    }
}

@BindingAdapter("setDate")
fun TextView.setDate(date: String?){
    date?.let {
//        val dateAndTime:List<String> = date.split('T')
        var originalFormat = SimpleDateFormat()
        if(date.contains('.')){
            originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        } else if(!date.contains('.')){
            originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        }
        val newDate = originalFormat.parse(date)
        val targetFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val formattedDate = targetFormat.format(newDate)

        this.text = formattedDate
    }
}
