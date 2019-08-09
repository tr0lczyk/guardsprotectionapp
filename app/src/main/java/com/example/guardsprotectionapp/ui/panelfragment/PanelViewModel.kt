package com.example.guardsprotectionapp.ui.panelfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.guardsprotectionapp.models.OfferModel
import com.example.guardsprotectionapp.network.GuardApi
import kotlinx.coroutines.*

class PanelViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "panelViewModel"

    val viewModelJob = Job()

    val panelViewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val offerList = MutableLiveData<List<OfferModel>>()

    init{
        getJobOffers()
    }

    fun getJobOffers(){
        panelViewModelScope.launch {
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
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}