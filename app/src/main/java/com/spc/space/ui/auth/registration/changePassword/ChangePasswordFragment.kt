package com.spc.space.ui.auth.registration.changePassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentChangePasswordBinding


class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChangePasswordBinding.bind(view)

        binding.btnResetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_changePasswordFragment_to_successNewPassFragment)
        }
    }
}