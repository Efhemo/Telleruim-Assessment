package com.efhem.farmapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.efhem.farmapp.R
import com.efhem.farmapp.data.local.StoragePref
import com.efhem.farmapp.databinding.FragmentLoginBinding
import com.efhem.farmapp.domain.Field
import com.efhem.farmapp.domain.FieldError
import com.efhem.farmapp.util.Utils
import org.koin.android.ext.android.inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    val storagePref: StoragePref by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        navController = NavHostFragment.findNavController(this)
        binding.btnSignIn.setOnClickListener {
            //navController.navigate(R.id.action_loginFragment_to_mainFragment)
            login()
        }
    }

    private fun login() {
        val email = binding.edlEmail.editText?.text.toString()
        val password = binding.edlPassword.editText?.text.toString()

        if (email.isEmpty() || !Utils.isEmail(email)) {
            binding.edlEmail.error = "Email is required"
            return
        } else binding.edlEmail.error = null

        if (password.isEmpty()) {
            binding.edlPassword.error = "password is required"
            return
        } else binding.edlPassword.error = null

        if ((email != storagePref.userEmail) or (password != storagePref.accessUserPassword)) {
            binding.edlEmail.error = "Incorrect email password combination"
            binding.edlPassword.error = "Incorrect email password combination"
            return
        } else navController.navigate(R.id.action_loginFragment_to_mainFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}