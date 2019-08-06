package com.example.guardsprotectionapp.ui.loginfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.guardsprotectionapp.databinding.FragmentLoginBinding
import timber.log.Timber

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        viewModel.userInputLogin.observe(this, Observer {
            it?.let {
                if (it.contains("@")) {
                    viewModel.invalidLogin(true)
                }
            }
        })

        viewModel.userInputPassword.observe(this, Observer {
            it?.let {
                if (it.toCharArray().size < 8) {
                    viewModel.invalidLogin(true)
                }
            }
        })

        return binding.root
    }
}