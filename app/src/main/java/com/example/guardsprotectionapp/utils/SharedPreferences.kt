package com.example.guardsprotectionapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import android.util.Log
import com.example.guardsprotectionapp.models.LoginResponse
import com.squareup.moshi.Moshi


class SharedPreferences(val context: Context) {

    private val SHARED_PREFS = "prefs"
    val sharedPref: SharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(KEY_NAME, text)

        editor.apply()
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putInt(KEY_NAME, value)

        editor.apply()
    }

    fun save(KEY_NAME: String, value: Long) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putLong(KEY_NAME, value)

        editor.apply()
    }

    fun save(KEY_NAME: String, status: Boolean) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putBoolean(KEY_NAME, status)

        editor.apply()
    }

    fun save(KEY_NAME: String, loginResponse: LoginResponse) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        val moshi = Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(LoginResponse::class.java)

        editor.putString(KEY_NAME, adapter.toJson(loginResponse))

        Log.i("TAG", "success")
        editor.apply()
    }

    fun getValueLogin(KEY_NAME: String): LoginResponse? {
        val moshi = Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(LoginResponse::class.java)
        val jsonString = sharedPref.getString(KEY_NAME, null)
        return adapter.fromJson(jsonString)
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.clear()
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.remove(KEY_NAME)
        editor.apply()
    }
}