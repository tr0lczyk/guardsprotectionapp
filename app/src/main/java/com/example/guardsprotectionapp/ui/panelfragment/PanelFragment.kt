package com.example.guardsprotectionapp.ui.panelfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.guardsprotectionapp.databinding.FragmentPanelBinding
import timber.log.Timber

class PanelFragment : Fragment() {

    private val viewModel: PanelViewModel by lazy {
        ViewModelProviders.of(this).get(PanelViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentPanelBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.currentButton.isChecked = true

        val adapter = OfferAdapter(
            OfferAdapter.OfferListener {
                Timber.d(it.id.toString())
                viewModel.declineOffer(it)
            },
            OfferAdapter.OfferListenerAccept {
                Timber.d(it.id.toString())
                viewModel.acceptOffer(it)
            })

        binding.offerRecycler.adapter = adapter
        binding.swipeButton.setOnRefreshListener {
            viewModel.getJobOffers()
        }

        viewModel.swipeRefreshing.observe(this, Observer {
            if (it) {
                binding.swipeButton.isRefreshing = false
                viewModel.swipeRefreshing.value = false
            }
        })

        viewModel.offerList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToLogin.observe(this, Observer {
            if (it) {
                findNavController().navigate(PanelFragmentDirections.actionPanelFragmentToLoginFragment())
                viewModel.navigateToLogin.value = false
            }
        })

        return binding.root
    }
}