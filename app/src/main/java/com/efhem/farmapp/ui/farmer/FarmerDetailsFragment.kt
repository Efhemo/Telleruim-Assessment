package com.efhem.farmapp.ui.farmer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.efhem.farmapp.R
import com.efhem.farmapp.databinding.FragmentFarmerDetailsBinding
import com.efhem.farmapp.domain.model.Farmer
import com.efhem.farmapp.domain.Field
import com.efhem.farmapp.domain.FieldError
import com.efhem.farmapp.ui.FarmViewModel
import com.efhem.farmapp.util.K
import com.efhem.farmapp.util.Utils
import com.efhem.farmapp.util.Utils.disableTextSelection
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FarmerDetailsFragment : Fragment(R.layout.fragment_farmer_details) {


    private var _bind: FragmentFarmerDetailsBinding? = null
    val bind get() = _bind!!

    private val viewModel by sharedViewModel<FarmViewModel>()

    private val imageContent = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        uri?.let {
            Glide.with(requireContext()).load(uri)
                .apply(RequestOptions.centerCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(bind.avatar)
            viewModel.setAvatar(uri.toString())
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentFarmerDetailsBinding.bind(view)

        initVIew()
        viewModel.observableFarmer.observe(viewLifecycleOwner, { farmer -> farmer?.let { setFarmer(farmer) } })
        viewModel.error.observe(viewLifecycleOwner, { it?.let { showViewError(it) } })
    }

    private fun initVIew() {

        bind.edlDob.editText?.disableTextSelection()

        bind.edlSurname.editText?.doAfterTextChanged { editable ->  viewModel.fields["surname"] = editable.toString() }
        bind.edlFirstName.editText?.doAfterTextChanged { editable ->   viewModel.fields["firstname"] = editable.toString() }
        bind.edlCity.editText?.doAfterTextChanged { editable ->  viewModel.fields["city"] = editable.toString() }
        bind.edlEmail.editText?.doAfterTextChanged { editable ->  viewModel.fields["email"] = editable.toString() }
        bind.edlDob.editText?.doAfterTextChanged { editable ->   viewModel.fields["dob"] = editable.toString() }
        bind.rgGender.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId == R.id.male){
                viewModel.setGender("Male")
            }else viewModel.setGender("Female")
        }
        bind.camera.setOnClickListener { imageContent.launch("image/*") }

        bind.edlDob.editText?.setOnClickListener {
            Utils.showDatePicker(childFragmentManager) {
                bind.edlDob.editText?.setText(it)
            }
        }
    }



    private fun setFarmer(farmer: Farmer) {
        Glide.with(requireContext()).load(farmer.avatar)
            .apply(RequestOptions.placeholderOf(R.drawable.glide_placeholder))
            .apply(RequestOptions.errorOf(R.drawable.glide_placeholder))
            .apply(RequestOptions.centerCropTransform())
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(bind.avatar)

        bind.edSurname.setText(farmer.surname)
        bind.edFirstName.setText(farmer.firstName)
        bind.edCity.setText(farmer.city)
        bind.edEmail.setText(farmer.email)
        farmer.gender.let {
            if (it == "Male") { bind.rgGender.check(R.id.male) } else bind.rgGender.check(R.id.female)
        }
        bind.edDob.setText(farmer.dob)
    }

    private fun showViewError(it: FieldError) {
        when (it.field) {
            Field.FIRST_NAME -> bind.edlFirstName.error = if (it.isError) it.error else null
            Field.SURNAME -> bind.edlSurname.error = if (it.isError) it.error else null
            Field.CITY -> bind.edlCity.error = if (it.isError) it.error else null
            Field.DOB -> bind.edlDob.error = if (it.isError) it.error else null
            Field.EMAIL -> bind.edlEmail.error = if (it.isError) it.error else null
            Field.AVATAR -> if (it.isError) Toast.makeText(requireContext(),"Select image", Toast.LENGTH_LONG).show()
            else -> if (it.isError) Toast.makeText(requireContext(), "Select a Gender", Toast.LENGTH_LONG).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FarmerDetailsFragment()
    }
}