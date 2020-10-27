package com.efhem.farmapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.efhem.farmapp.R
import com.efhem.farmapp.databinding.FragmentDashboardBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _bind: FragmentDashboardBinding? = null
    private val bind get() = _bind!!

    private val mainViewModel by sharedViewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentDashboardBinding.bind(view)

        mainViewModel.farmStat.observe(viewLifecycleOwner, Observer {
            bind.farmersCount.text = it.farmersCount.toString()

            bind.tvWithEmail.text = getString(R.string.win_percentage, it.farmersWithEmail)
            bind.progressBarFarmerWithEmail.progress = it.farmersWithEmail.toInt()

            bind.tvWithFarm.text = getString(R.string.win_percentage, it.farmersWithFarm)
            bind.progressBarFarmerWithFarm.progress = it.farmersWithFarm.toInt()

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = DashboardFragment()
    }
}