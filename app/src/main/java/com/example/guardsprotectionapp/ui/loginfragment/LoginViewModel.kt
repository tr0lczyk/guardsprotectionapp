package com.example.guardsprotectionapp.ui.loginfragment

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guardsprotectionapp.MainActivity.Companion.FIREBASE_TOKEN
import com.example.guardsprotectionapp.R
import com.example.guardsprotectionapp.models.LoginModel
import com.example.guardsprotectionapp.models.UserModel
import com.example.guardsprotectionapp.network.GuardApi
import com.example.guardsprotectionapp.utils.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.SocketTimeoutException

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val USER_LOGIN = "userLogin"
        const val USER_PASSWORD = "userPassword"
        const val USER = "user"
    }

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val sharedPreferences = SharedPreferences(getApplication())
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
        return (userInputLogin.value?.isEmpty()!! && userInputPassword.value!!.isEmpty())
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
                try {
                    val response = GuardApi.retrofitService.postLogin(
                        LoginModel(
                            userInputLogin.value!!,
                            userInputPassword.value!!,
                            true
                        )
                    )
                    response.let {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                sharedPreferences.save(USER, response.body()!!)
                                getUserData()
                            }
                        } else {
                            Timber.i(response.message())
                            progressVisibility.value = View.GONE
                        }
                    }
                } catch (e: SocketTimeoutException) {
                    Toast.makeText(
                        getApplication(),
                        "Cannot reach the server, please try again",
                        Toast.LENGTH_SHORT
                    ).show()
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

    fun getUserData() {
        coroutineScope.launch {
            try {
                sharedPreferences.getValueLogin(USER)?.let {
                    val id = sharedPreferences.getValueLogin(USER)!!.id
                    val response =
                        GuardApi.retrofitService.getUserData(id)
                    response.let {
                        if (response.isSuccessful) {
                            response.body().let {
                                val currentFirebaseToken = response.body()?.firebaseMobileAppToken
                                sharedPreferences.getValueString(FIREBASE_TOKEN).let {
                                    if (!currentFirebaseToken.equals(sharedPreferences.getValueString(FIREBASE_TOKEN))) {
                                        val currentUser = response.body()
                                        currentUser?.firebaseMobileAppToken = sharedPreferences.getValueString(FIREBASE_TOKEN)
                                        currentUser?.let{
                                            postNewFirebaseToken(currentUser)
                                        }
                                    } else {
                                        progressVisibility.value = View.GONE
                                        startNavigation.value = true
                                    }
                                }
                            }
                        } else {
                            progressVisibility.value = View.GONE
                            Timber.i(response.message())
                            Log.i("TAG", response.message())
                        }
                    }
                }
            } catch (e: SocketTimeoutException) {
                Toast.makeText(
                    getApplication(),
                    "Cannot reach the server, please try again",
                    Toast.LENGTH_SHORT
                ).show()
                progressVisibility.value = View.GONE
            }
        }
    }

    fun postNewFirebaseToken(userData: UserModel) {
        coroutineScope.launch {
            try {
                val response = GuardApi.retrofitService.postUserData(userData)
                response.let {
                    if(response.isSuccessful){
                        Log.i("TAG", "success")
                        startNavigation.value = true
                    } else {
                        progressVisibility.value = View.GONE
                        Log.i("TAG", response.message())
                    }
                }
            } catch (e: SocketTimeoutException) {
                Toast.makeText(
                    getApplication(),
                    "Cannot reach the server, please try again",
                    Toast.LENGTH_SHORT
                ).show()
                progressVisibility.value = View.GONE
            }
        }
    }

    fun isLoginButtonEnabled() {
        if (userInputLogin.value != null && userInputPassword.value != null) {
            if (areLoginPasswordValid() && !areLoginPasswordEmpty()) {
                loginButtonEnabled.value = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}