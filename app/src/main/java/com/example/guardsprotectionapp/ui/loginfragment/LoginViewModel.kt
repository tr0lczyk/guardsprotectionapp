package com.example.guardsprotectionapp.ui.loginfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _enableErrorLogin = MutableLiveData<Boolean>()

    val enableErrorLogin: LiveData<Boolean>
        get() = _enableErrorLogin

    private val _enableErrorPassword = MutableLiveData<Boolean>()

    val enableErrorPassword: LiveData<Boolean>
        get() = _enableErrorPassword

    val userInputLogin = MutableLiveData<String>()

    val userInputPassword = MutableLiveData<String>()

    init {
        _enableErrorLogin.value = true
        _enableErrorPassword.value = false
    }

    fun invalidLogin(isLoginValid: Boolean){
        _enableErrorLogin.value = isLoginValid
    }

    fun invalidPassword(isPasswordValid: Boolean){
        _enableErrorPassword.value = isPasswordValid
    }
}