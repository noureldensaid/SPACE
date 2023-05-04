package com.spc.space.ui.auth.registration.changePassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentChangePassword2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment(R.layout.fragment_change_password2) {
    private var _binding: FragmentChangePassword2Binding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChangePassword2Binding.bind(view)

        binding.btnResetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_changePasswordFragment_to_successNewPassFragment)
        }
    }
}