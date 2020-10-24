package com.efhem.farmapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.efhem.farmapp.R
import com.efhem.farmapp.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _bind: FragmentMainBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val bind get() = _bind!!

    private val fragments = listOf(DashboardFragment.newInstance(), FarmerListFragment.newInstance())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentMainBinding.bind(view)

        bind.pager.adapter = MainFragmentPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, fragments)

        TabLayoutMediator(bind.tabLayout, bind.pager) { tab, position -> tab.text = when (position) {
            0 -> "DashBoard" else -> "Farmers" }
        }.attach()
        bind.fabForm.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_mainFarmerFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind.pager.adapter = null
        _bind = null
    }
}

class MainFragmentPagerAdapter(
    fm: FragmentManager, lifecycle: Lifecycle, private val fragments: List<Fragment>
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = fragments.count()
    override fun createFragment(position: Int): Fragment = fragments[position]
}