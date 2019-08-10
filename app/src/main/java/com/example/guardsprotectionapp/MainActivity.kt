package com.example.guardsprotectionapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.guardsprotectionapp.ui.loginfragment.LoginViewModel.Companion.USER
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
        sharedPreferences.getValueString(USER)?.let {
            NavHostFragment.findNavController(mainHostFragment)
                .navigate(R.id.action_loginFragment_to_panelFragment, null, navOptions)
        }
    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(mainHostFragment).navigateUp()

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
