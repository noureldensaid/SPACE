package com.spc.space.ui.auth.registration.forgetPassword

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentForgetPasswordBinding
import com.spc.space.models.auth.forgetPassword.ForgetPasswordRequest
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment(R.layout.fragment_forget_password) {
    private val forgetPasswordViewModel: ForgetPasswordViewModel by viewModels()
    private var _binding: FragmentForgetPasswordBinding? = null
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentForgetPasswordBinding.bind(view)

        val token = dataStoreViewModel.token.value.toString()

        binding.signInTextView.setOnClickListener {
            findNavController().navigate(R.id.action_forgetPasswordFragment_to_loginFragment)
        }

        binding.apply {
            binding.btnSubmit.setOnClickListener {
                val email = editTextEmailForForgetPass.editText?.text.toString().trim()
                if (email.isNotEmpty()) {
                    val request = ForgetPasswordRequest(email)
                    forgetPasswordViewModel.forgetPassword(token, request)
                }
            }
        }


        forgetPasswordViewModel.forgetPassResponse.observe(viewLifecycleOwner, Observer { forgetPassResponse ->
                when (forgetPassResponse.message) {
                    "Done, please check your email" -> {
                        findNavController().navigate(R.id.action_forgetPasswordFragment_to_changePasswordFragment)
                    }
                    "User didn't register yet" -> {
                        Toast.makeText(
                            context,
                            "User didn't register yet",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            context,
                            "Invalid Inputs",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })

    }






}