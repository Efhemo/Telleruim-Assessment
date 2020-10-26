package com.efhem.farmapp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.efhem.farmapp.R
import com.efhem.farmapp.ui.adapters.FarmersAdapter
import com.efhem.farmapp.ui.adapters.Interaction
import com.efhem.farmapp.util.K
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FarmerListFragment : Fragment(R.layout.fragment_farmer_list) {

    private val mainViewModel by sharedViewModel<MainViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rc = view.findViewById<RecyclerView>(R.id.rc_farmers)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val rootView = view.findViewById<FrameLayout>(R.id.container)

        val adapter = setupRecyclerview(rc)

        mainViewModel.error.observe(viewLifecycleOwner, Observer {
            var snackBarDuration = Snackbar.LENGTH_LONG
            if(mainViewModel.observableFarmers.value.isNullOrEmpty()){
                snackBarDuration = Snackbar.LENGTH_INDEFINITE
            }
            Snackbar.make(rootView, it, snackBarDuration).setAction("Retry", View.OnClickListener {
                mainViewModel.fetchFarmers()
            }).show()
        })
        mainViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })
        mainViewModel.observableFarmers.observe(viewLifecycleOwner, Observer {
            adapter.swapData(it)
        })

        rc.adapter = adapter
    }

    private fun setupRecyclerview(rc: RecyclerView): FarmersAdapter {
        val verticalDecorator =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        val horizontalDecorator =
            DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
        val drawable =
            ResourcesCompat.getDrawable(resources, R.drawable.divider, resources.newTheme())
        if (drawable != null) {
            verticalDecorator.setDrawable(drawable)
            horizontalDecorator.setDrawable(drawable)
        }
        rc.addItemDecoration(verticalDecorator)
        rc.addItemDecoration(horizontalDecorator)
        return FarmersAdapter(Interaction {
            NavHostFragment.findNavController(this@FarmerListFragment)
                .navigate(R.id.action_mainFragment_to_mainFarmerFragment, Bundle().apply {
                    putParcelable(K.BUNDLE_ENTRY_FARMER, it)
                })
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = FarmerListFragment()
    }
}