package com.example.guardsprotectionapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.guardsprotectionapp.ui.loginfragment.LoginViewModel.Companion.USER
import com.example.guardsprotectionapp.utils.SharedPreferences
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "mainActivity"

    companion object {
        const val CHANNEL_ID = "guardians"
        private const val CHANNEL_NAME= "guardiansApp"
        private const val CHANNEL_DESC = "Notification channel"
        const val FIREBASE_TOKEN = "tokenFirebase"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = SharedPreferences(this)
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result?.token
                val msg = getString(R.string.msg_token_fmt, token)
                sharedPreferences.save(FIREBASE_TOKEN,"$token")
                Log.d(TAG, "$token")
            })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESC
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.loginFragment, true)
            .build()
        sharedPreferences.getValueString(USER)?.let {
            NavHostFragment.findNavController(mainHostFragment)
                .navigate(R.id.action_loginFragment_to_panelFragment, null, navOptions)
        }
    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(mainHostFragment).navigateUp()
}
