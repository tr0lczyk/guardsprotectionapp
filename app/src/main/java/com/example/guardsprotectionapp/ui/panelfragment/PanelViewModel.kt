package com.example.guardsprotectionapp.ui.panelfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.guardsprotectionapp.network.GuardApi
import kotlinx.coroutines.*

class PanelViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "panelViewModel"

    val viewModelJob = Job()

    val panelViewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init{
        getJobOffers()
    }

    fun getJobOffers(){
        panelViewModelScope.launch {
            val response = GuardApi.retrofitService.getJobOffers()
            response.let {
                if(response.isSuccessful){
                    Log.i(TAG,"success")
                } else {
                    Log.i(TAG,response.message())
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}