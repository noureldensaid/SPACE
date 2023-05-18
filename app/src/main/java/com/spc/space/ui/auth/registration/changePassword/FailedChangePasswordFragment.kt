package com.spc.space.ui.auth.registration.changePassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentFailedChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FailedChangePasswordFragment : Fragment(R.layout.fragment_failed_change_password) {
    private var _binding: FragmentFailedChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFailedChangePasswordBinding.bind(view)

        binding.btnTryAgain.setOnClickListener {
            findNavController().navigate(R.id.action_failedChangePasswordFragment_to_forgetPasswordFragment)
        }
    }
}