package com.example.guardsprotectionapp.ui.loginfragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.guardsprotectionapp.R
import com.example.guardsprotectionapp.databinding.FragmentLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.userInputLogin.observe(this, Observer {
            if (!it.contains("@") && it.isNotEmpty()) {
                viewModel.invalidLogin(true)
                binding.textInputLayoutLogin.error = getString(R.string.at_char)
            } else if (it.contains("@") || it.isEmpty()) {
                viewModel.invalidLogin(false)
                binding.textInputLayoutLogin.error = null
            }
            viewModel.isLoginButtonEnabled()
        })

        viewModel.userInputPassword.observe(this, Observer {
            if (it.toCharArray().size < 4 && it.isNotEmpty()) {
                viewModel.invalidPassword(true)
                binding.textInputLayoutPassword.error = getString(R.string.pass_char)
            } else if (it.toCharArray().size >= 4 || it.isEmpty()) {
                viewModel.invalidPassword(false)
                binding.textInputLayoutPassword.error = null
            }
            viewModel.isLoginButtonEnabled()
        })

        viewModel.startNavigation.observe(this, Observer {
            if(it){
                this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToPanelFragment())
                UIUtil.hideKeyboard(activity)
                viewModel.startNavigation.value = false
            }
        })

        viewModel.cannotConnect.observe(this, Observer {
            if(it){
                UIUtil.hideKeyboard(activity)
                openDialogCantConnect()
                viewModel.cannotConnect.value = false
            }
        })

        return binding.root
    }

    fun openDialogCantConnect(){
        val builder = AlertDialog.Builder(activity)
            .setTitle("Unable to connect")
            .setMessage("Please verify if your device is connected to the internet and try again")
            .setPositiveButton("OK"){dialog, which ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .create()
        builder.show()
    }
}