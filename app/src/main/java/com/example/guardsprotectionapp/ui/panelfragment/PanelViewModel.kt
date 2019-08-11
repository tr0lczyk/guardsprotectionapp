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
import com.example.guardsprotectionapp.models.*
import com.example.guardsprotectionapp.network.GuardApi
import com.example.guardsprotectionapp.ui.loginfragment.LoginViewModel.Companion.USER
import com.example.guardsprotectionapp.utils.SharedPreferences
import kotlinx.coroutines.*
import timber.log.Timber
import java.net.SocketTimeoutException

class PanelViewModel(application: Application) : AndroidViewModel(application) {

    enum class EmployeeStatusId(val status: Int) {
        NOTSEND(100),
        INBOX(1),
        ACCEPTED(2),
        DECLINED(0)
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


    init {
        whichList = 1
        swipeRefreshing.value = false
        panelProgressVisibility.value = View.GONE
        getJobOffers()
        mainOfferList = ArrayList()
        currentList = ArrayList()
        user = sharedPrefs.getValueLogin(USER)
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
                            Toast.makeText(getApplication(),"Your request has been send", Toast.LENGTH_SHORT).show()
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
                    if (j.employeeStatus.id == EmployeeStatusId.INBOX.status) {
                        currentList.add(i)
                    }
                }
            }
        }
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
                    if (j.employeeStatus.id == EmployeeStatusId.DECLINED.status) {
                        currentList.add(i)
                    }
                }
            }
        }
        _offerList.value = currentList
    }

    fun getAcceptedJobOffers() {
        whichList = 2
        currentList = ArrayList()
//        currentList.clear()
        highlightSingleButton(R.color.colorPrimary, R.color.colorPrimary, R.color.green)
        for (i in mainOfferList) {
            val filteredEployees: List<EmployeeModel>? =
                i.assignedEmployees?.filter {
                    it.employeeId == user!!.id
                }
            if (filteredEployees != null) {
                for (j in filteredEployees) {
                    if (j.employeeStatus.id == EmployeeStatusId.ACCEPTED.status) {
                        currentList.add(i)
                    }
                }
            }
        }
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
}