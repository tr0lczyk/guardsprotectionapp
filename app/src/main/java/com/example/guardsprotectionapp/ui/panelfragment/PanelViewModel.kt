package com.example.guardsprotectionapp.ui.panelfragment

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guardsprotectionapp.R
import com.example.guardsprotectionapp.database.GuardDatabase
import com.example.guardsprotectionapp.models.*
import com.example.guardsprotectionapp.network.GuardApi
import com.example.guardsprotectionapp.ui.loginfragment.LoginViewModel
import com.example.guardsprotectionapp.ui.loginfragment.LoginViewModel.Companion.USER
import com.example.guardsprotectionapp.utils.SharedPreferences
import kotlinx.coroutines.*
import timber.log.Timber
import java.net.SocketTimeoutException

class PanelViewModel(application: Application) : AndroidViewModel(application) {

    enum class EmployeeStatusId(val status: Int) {
        NOTSEND(100),
        INBOX(1),
        ACCEPTED(0),
        DECLINED(2)
    }

    val TAG = "panelViewModel"

    val viewModelJob = Job()
    val panelViewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    var mainOfferList: List<OfferModel>
    var currentList: MutableList<OfferModel>
    val sharedPrefs = SharedPreferences(application)
    val user: LoginResponse?
    var whichList: Int

    private val _offerList = MutableLiveData<List<OfferModel>>()
    val offerList: LiveData<List<OfferModel>>
        get() = _offerList

    val declinedStrokeColor = MutableLiveData<Int>()
    val inboxStrokeColor = MutableLiveData<Int>()
    val acceptedStrokeColor = MutableLiveData<Int>()
    val panelProgressVisibility = MutableLiveData<Int>()
    var firstTime = true
    val swipeRefreshing = MutableLiveData<Boolean>()
    var emptyListVisibility =  MutableLiveData<Int>()
    var navigateToLogin = MutableLiveData<Boolean>()
    val database: GuardDatabase

    init {
        whichList = 1
        navigateToLogin.value = false
        emptyListVisibility.value = View.GONE
        swipeRefreshing.value = false
        panelProgressVisibility.value = View.GONE
        getJobOffers()
        mainOfferList = ArrayList()
        currentList = ArrayList()
        user = sharedPrefs.getValueLogin(USER)
        database = GuardDatabase.invoke(application)
    }

    fun getJobOffers() {
        if (firstTime) {
            highlightSingleButton(
                R.color.colorPrimary,
                R.color.loginButtonDarker,
                R.color.colorPrimary)
            panelProgressVisibility.value = View.VISIBLE
            firstTime = false
        } else {
            swipeRefreshing
        }
        panelViewModelScope.launch {
            try {
                val response = GuardApi.retrofitService.getJobOffers()
                response.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            swipeRefreshing.value = true
                            mainOfferList = response.body()!!
                            when (whichList) {
                                0 -> getDeclinedJobOffers()
                                1 -> getInboxJobOffers()
                                2 -> getAcceptedJobOffers()
                            }
                            verifyEmptyList()
                            panelProgressVisibility.value = View.GONE
                        }
                    } else {
                        Timber.i(response.message())
                        swipeRefreshing.value = true
                        _offerList.value = ArrayList()
                        panelProgressVisibility.value = View.GONE
                    }
                }
            } catch (e: SocketTimeoutException) {
                swipeRefreshing.value = true
                Toast.makeText(
                    getApplication(),
                    "Cannot reach the server, please try again",
                    Toast.LENGTH_SHORT
                ).show()
                panelProgressVisibility.value = View.GONE
            }
        }
    }

    fun saveOffers(vararg offers: OfferModel){
        panelViewModelScope.launch {
            database.OfferModelDao().insertAll(*offers)
        }
    }

    fun declineOffer(offer: OfferModel) {
        panelProgressVisibility.value = View.VISIBLE
        val employeeModelStatus = EmployeeStatusModel(
            offer.id,
            user!!.id,
            EmployeeStatusId.DECLINED.status
        )
        panelViewModelScope.launch {
            try {
                val response = GuardApi.retrofitService.postEmployeeAcceptance(employeeModelStatus)
                response.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            getJobOffers()
                        }
                        panelProgressVisibility.value = View.GONE
                    } else {
                        Log.i(TAG, response.message())
                        panelProgressVisibility.value = View.GONE
                    }
                }
            } catch (e: SocketTimeoutException) {
                Toast.makeText(
                    getApplication(),
                    "Cannot reach the server, please try again",
                    Toast.LENGTH_SHORT
                ).show()
                panelProgressVisibility.value = View.GONE
            }
        }
    }

    fun acceptOffer(offer: OfferModel) {
        panelProgressVisibility.value = View.VISIBLE
        val employeeModelStatus = EmployeeStatusModel(
            offer.id,
            user!!.id,
            EmployeeStatusId.ACCEPTED.status
        )
        panelViewModelScope.launch {
            try {
                val response = GuardApi.retrofitService.postEmployeeAcceptance(employeeModelStatus)
                response.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Toast.makeText(getApplication(),"Your request has been send",
                                Toast.LENGTH_SHORT).show()
                            getJobOffers()
                            panelProgressVisibility.value = View.GONE
                        }
                    } else {
                        Log.i(TAG, response.message())
                        panelProgressVisibility.value = View.GONE
                    }
                }
            } catch (e: SocketTimeoutException) {
                Toast.makeText(
                    getApplication(),
                    "Cannot reach the server, please try again",
                    Toast.LENGTH_SHORT
                ).show()
                panelProgressVisibility.value = View.GONE
            }
        }
    }

    fun getInboxJobOffers() {
        whichList = 1
        currentList = ArrayList()
        highlightSingleButton(R.color.colorPrimary, R.color.loginButtonDarker, R.color.colorPrimary)
        for (i in mainOfferList) {
            val filteredEployees: List<EmployeeModel>? =
                i.assignedEmployees?.filter {
                    it.employeeId == user!!.id
                }
            if (filteredEployees != null) {
                for (j in filteredEployees) {
                    if (j.employeeStatus.id != EmployeeStatusId.DECLINED.status &&
                            j.status.id == 0) {
                        currentList.add(i)
                    }
                }
            }
        }
        verifyEmptyList()
        _offerList.value = currentList
    }

    fun getDeclinedJobOffers() {
        whichList = 0
        currentList = ArrayList()
        highlightSingleButton(R.color.red, R.color.colorPrimary, R.color.colorPrimary)
        for (i in mainOfferList) {
            val filteredEployees: List<EmployeeModel>? =
                i.assignedEmployees?.filter {
                    it.employeeId == user!!.id
                }
            if (filteredEployees != null) {
                for (j in filteredEployees) {
                    if (j.employeeStatus.id == EmployeeStatusId.DECLINED.status ||
                        j.status.id == EmployeeStatusId.DECLINED.status ) {
                        currentList.add(i)
                    }
                }
            }
        }
        verifyEmptyList()
        _offerList.value = currentList
    }

    fun getAcceptedJobOffers() {
        whichList = 2
        currentList = ArrayList()
        highlightSingleButton(R.color.colorPrimary, R.color.colorPrimary, R.color.green)
        for (i in mainOfferList) {
            val filteredEployees: List<EmployeeModel>? =
                i.assignedEmployees?.filter {
                    it.employeeId == user!!.id
                }
            if (filteredEployees != null) {
                for (j in filteredEployees) {
                    if (j.employeeStatus.id == EmployeeStatusId.ACCEPTED.status &&
                        j.status.id == 1) {
                        currentList.add(i)
                    }
                }
            }
        }
        verifyEmptyList()
        _offerList.value = currentList
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun highlightSingleButton(colorD: Int, colorI: Int, colorA: Int) {
        declinedStrokeColor.value = ContextCompat.getColor(getApplication(), colorD)
        inboxStrokeColor.value = ContextCompat.getColor(getApplication(), colorI)
        acceptedStrokeColor.value = ContextCompat.getColor(getApplication(), colorA)
    }

    fun verifyEmptyList(){
        if(currentList.isEmpty()){
            emptyListVisibility.value = View.VISIBLE
        } else {
            emptyListVisibility.value = View.GONE
        }
    }

    fun logoutUser(){
        sharedPrefs.removeValue(USER)
        navigateToLogin.value = true
    }

//    fun getCustomersData() {
//        panelViewModelScope.launch {
//            try {
//                val response = GuardApi.retrofitService.getCustomersData(28)
//                response.let {
//                    if(response.isSuccessful){
//                        Log.i("TAG", "success")
//                    } else {
//                        Timber.i(response.message())
//                        Log.i("TAG", response.message())
//                    }
//                }
//            } catch (e: SocketTimeoutException) {
//                Toast.makeText(
//                    getApplication(),
//                    "Cannot reach the server, please try again",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }

//    fun postNewFirebaseToken(userData: UserModel) {
//        panelViewModelScope.launch {
//            try {
//                val response = GuardApi.retrofitService.postUserData(userData)
//                response.let {
//                    if(response.isSuccessful){
//                        Log.i("TAG", "success")
//                    } else {
//                        Timber.i(response.message())
//                        Log.i("TAG", response.message())
//                    }
//                }
//            } catch (e: SocketTimeoutException) {
//                Toast.makeText(
//                    getApplication(),
//                    "Cannot reach the server, please try again",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
}