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

    init{
        panelProgressVisibility.value = View.GONE
        getJobOffers()
    }

    fun getJobOffers(){
        panelProgressVisibility.value = View.VISIBLE
        declinedStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.colorPrimary)
        inboxStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.loginButtonDarker)
        acceptedStrokeColor.value = ContextCompat.getColor(getApplication(), R.color.colorPrimary)
        panelViewModelScope.launch {
            try{
                val response = GuardApi.retrofitService.getJobOffers()
                response.let {
                    if(response.isSuccessful){
                        Log.i(TAG,"success")
                        response.body()?.let {
                            offerList.value = response.body()
                        }
                    } else {
                        Log.i(TAG,response.message())
                        offerList.value = ArrayList()
                    }
                    panelProgressVisibility.value = View.GONE
                }
            } catch (e: SocketTimeoutException){
                Toast.makeText(
                    getApplication(),
                    "Cannot reach the server, please try again",
                    Toast.LENGTH_SHORT).show()
            }

        }
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