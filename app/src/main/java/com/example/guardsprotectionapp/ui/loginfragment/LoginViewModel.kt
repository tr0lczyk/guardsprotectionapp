package com.example.guardsprotectionapp.ui.loginfragment

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guardsprotectionapp.R
import com.example.guardsprotectionapp.models.LoginModel
import com.example.guardsprotectionapp.network.GuardApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _enableErrorLogin = MutableLiveData<Boolean>()

    val enableErrorLogin: LiveData<Boolean>
        get() = _enableErrorLogin

    private val _enableErrorPassword = MutableLiveData<Boolean>()

    val enableErrorPassword: LiveData<Boolean>
        get() = _enableErrorPassword

    private val loginButtonEnabled = MutableLiveData<Boolean>()

    val userInputLogin = MutableLiveData<String>()

    val userInputPassword = MutableLiveData<String>()

    val progressVisibility = MutableLiveData<Int>()

    val startNavigation = MutableLiveData<Boolean>()

    private fun areLoginPasswordValid(): Boolean {
        return (!enableErrorLogin.value!! && !enableErrorPassword.value!!)
    }

    private fun areLoginPasswordEmpty(): Boolean {
        return (userInputLogin.value!!.isEmpty() && userInputPassword.value!!.isEmpty())
    }

    init {
        _enableErrorLogin.value = false
        _enableErrorPassword.value = false
        loginButtonEnabled.value = false
        progressVisibility.value = View.GONE
    }

    fun invalidLogin(isLoginValid: Boolean) {
        _enableErrorLogin.value = isLoginValid
    }

    fun invalidPassword(isPasswordValid: Boolean) {
        _enableErrorPassword.value = isPasswordValid
    }

    fun userLogin() {
        if (loginButtonEnabled.value!!) {
            progressVisibility.value = View.VISIBLE
            coroutineScope.launch {
                val response = GuardApi.retrofitService.postLogin(
                    LoginModel(
                        userInputLogin.value!!,
                        userInputPassword.value!!,
                        true
                    )
                )
                response.let {
                    if (response.isSuccessful) {
                        Toast.makeText(getApplication(), "success", Toast.LENGTH_SHORT).show()
                        startNavigation.value = true
                    } else {
                        Toast.makeText(getApplication(), response.message(), Toast.LENGTH_SHORT).show()
                    }
                    progressVisibility.value = View.GONE
                }
            }
        } else {
            Toast.makeText(
                getApplication()
                , getApplication<Application>().resources.getString(R.string.login_button_toast)
                , Toast.LENGTH_LONG
            ).show()
        }
    }

    fun isLoginButtonEnabled() {
        if (areLoginPasswordValid() && !areLoginPasswordEmpty()) {
            loginButtonEnabled.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}