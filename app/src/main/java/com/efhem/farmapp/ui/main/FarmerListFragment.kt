package com.efhem.farmapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.efhem.farmapp.R
import com.efhem.farmapp.domain.Farmer
import com.efhem.farmapp.ui.adapters.FarmersAdapter

class FarmerListFragment : Fragment(R.layout.fragment_farmer_list) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rc = view.findViewById<RecyclerView>(R.id.rc_farmers)

        val verticalDecorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        val horizontalDecorator = DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)

        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.divider, resources.newTheme())
        if (drawable != null) {
            verticalDecorator.setDrawable(drawable)
            horizontalDecorator.setDrawable(drawable)
        }
        rc.addItemDecoration(verticalDecorator)
        rc.addItemDecoration(horizontalDecorator)

        val adapter = FarmersAdapter(object : FarmersAdapter.Interaction {
            override fun onFarmersClick(farmer: Farmer) {
                NavHostFragment.findNavController(this@FarmerListFragment).navigate(R.id.action_mainFragment_to_mainFarmerFragment)
            }
        })

        rc.adapter = adapter
        adapter.swapData(MainViewModel.listFarmers)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FarmerListFragment()
    }
}