package com.example.guardsprotectionapp.utils

import java.text.SimpleDateFormat
import java.util.*


class Utilities {

    companion object {
        fun convertDateToString(date: Date): String {
            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            return originalFormat.format(date)
        }
    }

}
