package com.spc.space.ui.auth.registration.changePassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.spc.space.R

import com.spc.space.databinding.FragmentSuccessNewPassBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessNewPassFragment : Fragment(R.layout.fragment_success_new_pass) {
    private var _binding: FragmentSuccessNewPassBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSuccessNewPassBinding.bind(view)

        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_successNewPassFragment_to_loginFragment)
        }
    }
}

