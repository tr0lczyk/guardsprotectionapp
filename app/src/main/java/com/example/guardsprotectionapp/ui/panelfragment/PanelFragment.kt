package com.example.guardsprotectionapp.ui.panelfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.guardsprotectionapp.databinding.FragmentPanelBinding

class PanelFragment: Fragment(){

    private val viewModel: PanelViewModel by lazy {
        ViewModelProviders.of(this).get(PanelViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentPanelBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.currentButton.isChecked = true
        binding.offerRecycler.adapter = OfferAdapter(OfferAdapter.OfferListener {
            Toast.makeText(activity, "item id is ${it.id}",Toast.LENGTH_SHORT).show()
        })
        return binding.root
    }
}