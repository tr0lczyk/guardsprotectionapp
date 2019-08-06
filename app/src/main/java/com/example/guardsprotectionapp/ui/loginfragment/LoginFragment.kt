package com.example.guardsprotectionapp.ui.loginfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.guardsprotectionapp.R
import com.example.guardsprotectionapp.databinding.FragmentLoginBinding
import timber.log.Timber

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.userInputLogin.observe(this, Observer {
                if (!it.contains("@") && !it.isEmpty()) {
                    viewModel.invalidLogin(true)
                    binding.textInputLayoutLogin.error = getString(R.string.at_char)
                } else if(it.contains("@") || it.isEmpty()) {
                    viewModel.invalidLogin(false)
                    binding.textInputLayoutLogin.error = null
                }
        })

        viewModel.userInputPassword.observe(this, Observer {
            if (binding.textInputLayoutPassword.hasFocus()){
                    if (it.toCharArray().size < 8) {
                        viewModel.invalidPassword(true)
                        binding.textInputLayoutPassword.error = getString(R.string.pass_char)
                    } else if (it.toCharArray().size >= 8 || it.isEmpty()){
                        viewModel.invalidPassword(false)
                        binding.textInputLayoutPassword.error = null
                    }
            }
        })
        return binding.root
    }
}