package com.example.guardsprotectionapp.utils


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.guardsprotectionapp.R
import com.example.guardsprotectionapp.models.EmployeeModel
import com.example.guardsprotectionapp.models.OfferModel
import com.example.guardsprotectionapp.ui.loginfragment.LoginViewModel.Companion.USER
import com.example.guardsprotectionapp.ui.panelfragment.OfferAdapter
import com.example.guardsprotectionapp.ui.panelfragment.PanelViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat


@BindingAdapter("errorText")
fun TextInputLayout.setErrorText(text: String?) {
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
fun ImageView.loadNewImage(link: String?) {
    link?.let {
        val decodedString = Base64.decode(link, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        val byteArrayOutputStream = ByteArrayOutputStream()
        decodedByte.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
        val byte: ByteArray = byteArrayOutputStream.toByteArray()
        val bitmap2 = BitmapFactory.decodeByteArray(byte, 0, byte.size)
        this.setImageBitmap(bitmap2)
    }
}

@BindingAdapter("setDate")
fun TextView.setDate(date: String?) {
    date?.let {
        //        val dateAndTime:List<String> = date.split('T')
        var originalFormat = SimpleDateFormat()
        if (date.contains('.')) {
            originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        } else if (!date.contains('.')) {
            originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        }
        val newDate = originalFormat.parse(date)
        val targetFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val formattedDate = targetFormat.format(newDate)

        this.text = formattedDate
    }
}

@BindingAdapter("changeStrokeColor")
fun MaterialCardView.setNewColor(assignedEmployees: List<EmployeeModel>) {
    val sharedPreferences = SharedPreferences(context)
    val user = sharedPreferences.getValueLogin(USER)
    val filteredEmployees: List<EmployeeModel>? =
        assignedEmployees?.filter {
            it.employeeId == user!!.id
        }
    if (filteredEmployees != null) {
        for (j in filteredEmployees) {
            if (j.employeeStatus.id != PanelViewModel.EmployeeStatusId.DECLINED.status &&
                j.status.id == 0
            ) {
                this.strokeColor = ContextCompat.getColor(context, R.color.loginButtonDarker)
            } else if (j.employeeStatus.id == PanelViewModel.EmployeeStatusId.DECLINED.status ||
                j.status.id == PanelViewModel.EmployeeStatusId.DECLINED.status
            ) {
                this.strokeColor = ContextCompat.getColor(context, R.color.red)
            } else {
                this.strokeColor = ContextCompat.getColor(context, R.color.green)
            }
        }
    }
}

@BindingAdapter("buttonsVisibility")
fun MaterialButton.setButtonsVisibility(assignedEmployees: List<EmployeeModel>) {
    val sharedPreferences = SharedPreferences(context)
    val user = sharedPreferences.getValueLogin(USER)
    val filteredEmployees: List<EmployeeModel>? =
        assignedEmployees?.filter {
            it.employeeId == user!!.id
        }
    if (filteredEmployees != null) {
        for (j in filteredEmployees) {
            this.visibility = when (j.employeeStatus.id) {
                PanelViewModel.EmployeeStatusId.INBOX.status -> View.VISIBLE
                else -> View.GONE
            }
        }
    }
}

@BindingAdapter("acceptedVisibility")
fun TextView.setAcceptedTextVisibility(assignedEmployees: List<EmployeeModel>) {
    val sharedPreferences = SharedPreferences(context)
    val user = sharedPreferences.getValueLogin(USER)
    val filteredEmployees: List<EmployeeModel>? =
        assignedEmployees?.filter {
            it.employeeId == user!!.id
        }
    if (filteredEmployees != null) {
        for (j in filteredEmployees) {
            if (j.employeeStatus.id == PanelViewModel.EmployeeStatusId.ACCEPTED.status
                && j.status.id == 1
            ) {
                this.visibility = View.VISIBLE
                this.setTextColor(ContextCompat.getColor(context, R.color.green))
                this.text = context.getString(R.string.accepted)
            } else if (j.employeeStatus.id == PanelViewModel.EmployeeStatusId.ACCEPTED.status
                && j.status.id == 0
            ) {
                this.visibility = View.VISIBLE
                this.setTextColor(ContextCompat.getColor(context, R.color.loginButtonDarker))
                this.text = context.getString(R.string.send_to_super)
            } else {
                this.visibility = View.GONE
            }
        }
    }
}

@BindingAdapter("declinedVisibility")
fun TextView.setDeclinedsTextVisibility(assignedEmployees: List<EmployeeModel>) {
    val sharedPreferences = SharedPreferences(context)
    val user = sharedPreferences.getValueLogin(USER)
    val filteredEmployees: List<EmployeeModel>? =
        assignedEmployees?.filter {
            it.employeeId == user!!.id
        }
    if (filteredEmployees != null) {
        for (j in filteredEmployees) {
            if (j.employeeStatus.id == PanelViewModel.EmployeeStatusId.DECLINED.status ||
                j.status.id == PanelViewModel.EmployeeStatusId.DECLINED.status
            ) {
                this.visibility = View.VISIBLE
            } else {
                this.visibility = View.GONE
            }
        }
    }
}

