package com.spc.space.ui.auth.registration.forgetPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentForgetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment(R.layout.fragment_forget_password) {
    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentForgetPasswordBinding.bind(view)

        binding.signInTextView.setOnClickListener{
            findNavController().navigate(R.id.action_forgetPasswordFragment_to_loginFragment)
        }

        binding.btnSubmit.setOnClickListener{
            findNavController().navigate(R.id.action_forgetPasswordFragment_to_OtpFragment)
        }


    }



}