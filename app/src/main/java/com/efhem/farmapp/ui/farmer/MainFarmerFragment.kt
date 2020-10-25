package com.efhem.farmapp.ui.farmer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.efhem.farmapp.R
import com.efhem.farmapp.databinding.FragmentMainFarmerBinding
import com.efhem.farmapp.domain.Farmer
import com.efhem.farmapp.ui.FarmViewModel
import com.efhem.farmapp.util.K
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFarmerFragment : Fragment(R.layout.fragment_main_farmer), View.OnClickListener {

    private var _bind: FragmentMainFarmerBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val bind get() = _bind!!
    private var navController: NavController? = null

    private val viewModel by sharedViewModel<FarmViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getParcelable<Farmer>(K.BUNDLE_ENTRY_FARMER)?.let { viewModel.setFarmer(it) }
        }
    }

    private val fragments =
        listOf( FarmerDetailsFragment.newInstance(), FarmLocationFragment.newInstance() )

    private val listener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when (position) {
                0 -> {
                    bind.btnNext.visibility = View.VISIBLE
                    bind.containerBtnPrevious.visibility = View.GONE
                }
                1 -> {
                    bind.btnNext.visibility = View.GONE
                    bind.containerBtnPrevious.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentMainFarmerBinding.bind(view)

        navController = NavHostFragment.findNavController(this)

        bind.pager.apply {
            adapter = MainFragmentPagerAdapter(childFragmentManager, lifecycle, fragments)
            isUserInputEnabled = false
            isSaveEnabled  = false
            offscreenPageLimit = 2
            registerOnPageChangeCallback(listener)
        }

        bind.btnNext.setOnClickListener(this)
        bind.btnNextPage.setOnClickListener(this)
        bind.btnPreviousPage.setOnClickListener(this)
        bind.appbar.btnBackArrow.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind.pager.unregisterOnPageChangeCallback(listener)
        _bind = null
    }

    override fun onClick(v: View?) {
        v?.let { view ->
            val pager = bind.pager
            when (view.id) {
                R.id.btn_back_arrow -> navController?.popBackStack()
                R.id.btn_next -> {
                    if(viewModel.isFormValidated()){
                        println("filled farmer form ${viewModel.getFilledFarmerForm()}")
                        pager.currentItem = 1
                    }else null
                }
                R.id.btn_previous_page -> {
                    if (pager.currentItem != 0) {
                        pager.currentItem = 0
                    } else null
                }
                R.id.btn_next_page -> {
                }
                else -> null
            }
        }
    }

}

class MainFragmentPagerAdapter(
    fm: FragmentManager, lifecycle: Lifecycle, private val fragments: List<Fragment>
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = fragments.count()
    override fun createFragment(position: Int): Fragment = fragments[position]
}