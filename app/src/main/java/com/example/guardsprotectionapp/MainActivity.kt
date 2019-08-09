package com.example.guardsprotectionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Carriers.USER
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.guardsprotectionapp.utils.SharedPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = SharedPreferences(this)
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.loginFragment, true)
            .build()
        sharedPreferences.getValueLogin(USER).let {
            NavHostFragment.findNavController(mainHostFragment)
                .navigate(R.id.action_loginFragment_to_panelFragment, null, navOptions)
        }
    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(mainHostFragment).navigateUp()

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
