package com.example.guardsprotectionapp.ui.loginfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guardsprotectionapp.models.LoginModel
import com.example.guardsprotectionapp.network.GuardApi
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _enableErrorLogin = MutableLiveData<Boolean>()

    val enableErrorLogin: LiveData<Boolean>
        get() = _enableErrorLogin

    private val _enableErrorPassword = MutableLiveData<Boolean>()

    val enableErrorPassword: LiveData<Boolean>
        get() = _enableErrorPassword

    init {
        _enableErrorLogin.value = false
        _enableErrorPassword.value = false
    }

    val userInputLogin = MutableLiveData<String>()

    val userInputPassword = MutableLiveData<String>()

    fun invalidLogin(isLoginValid: Boolean) {
        _enableErrorLogin.value = isLoginValid
    }

    fun invalidPassword(isPasswordValid: Boolean) {
        _enableErrorPassword.value = isPasswordValid
    }

    fun userLogin() {
        coroutineScope.launch {
            try{
                val response = GuardApi.retrofitService.postLogin(
                    LoginModel(
                        userInputLogin.value!!,
                        userInputPassword.value!!,
                        true
                    )
                )
                Log.i("TAG","${userInputLogin.value!!}")
                Log.i("TAG","${userInputPassword.value!!}")
                response?.let {
                    Log.i("TAG", "${response.accountId}")
                    Log.i("TAG", "${response.name}")
                }


            } catch (e: HttpException){
                Log.e("TAG", "Exception " + e.printStackTrace())
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}