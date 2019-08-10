package com.example.guardsprotectionapp.ui.panelfragment

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.guardsprotectionapp.R
import com.example.guardsprotectionapp.models.OfferModel
import com.example.guardsprotectionapp.network.GuardApi
import kotlinx.coroutines.*
import java.net.SocketTimeoutException

class PanelViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "panelViewModel"

    val viewModelJob = Job()
    val panelViewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val offerList = MutableLiveData<List<OfferModel>>()
    val declinedStrokeColor = MutableLiveData<Int>()
    val inboxStrokeColor = MutableLiveData<Int>()
    val acceptedStrokeColor = MutableLiveData<Int>()
    val panelProgressVisibility = MutableLiveData<Int>()
    var firstTime = true
    val swipeRefreshing = MutableLiveData<Boolean>()

    init{
        swipeRefreshing.value = false
        panelProgressVisibility.value = View.GONE
        getJobOffers()
    }

    fun getJobOffers(){
        if(firstTime){
            panelProgressVisibility.value = View.VISIBLE
            firstTime = false
        } else {
            swipeRefreshing
        }

        getInboxJobOffers()
        panelViewModelScope.launch {
            try{
                val response = GuardApi.retrofitService.getJobOffers()
                response.let {
                    if(response.isSuccessful){
                        response.body()?.let {
                            swipeRefreshing.value = true
                            offerList.value = response.body()
                            panelProgressVisibility.value = View.GONE
                        }
                    } else {
                        Log.i(TAG,response.message())
                        swipeRefreshing.value = true
                        offerList.value = ArrayList()
                        panelProgressVisibility.value = View.GONE
                    }
                }
            } catch (e: SocketTimeoutException){
                swipeRefreshing.value = true
                Toast.makeText(
                    getApplication(),
                    "Cannot reach the server, please try again",
                    Toast.LENGTH_SHORT).show()
                panelProgressVisibility.value = View.GONE
            }

        }
    }

    fun getInboxJobOffers(){
        declinedStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.colorPrimary)
        inboxStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.loginButtonDarker)
        acceptedStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.colorPrimary)
    }

    fun getDeclinedJobOffers(){
        declinedStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.red)
        inboxStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.colorPrimary)
        acceptedStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.colorPrimary)
    }

    fun getAcceptedJobOffers(){
        declinedStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.colorPrimary)
        inboxStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.colorPrimary)
        acceptedStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.green)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}