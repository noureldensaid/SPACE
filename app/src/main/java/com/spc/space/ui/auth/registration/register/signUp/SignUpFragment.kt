package com.spc.space.ui.auth.registration.register.signUp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentSignUpBinding
import com.spc.space.models.auth.signUp.SignUpRequest
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val signUpViewModel: SignUpViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)


        //  get sign up data the wrap it in sign up request
        binding.apply {
            signUpBtn.setOnClickListener {
                val username = usernameEt.editText?.text.toString().lowercase().trim()
                val email = emailEt.editText?.text.toString().lowercase().trim()
                val phone = phoneEt.editText?.text.toString().trim()
                val password = passwordEt.editText?.text.toString().trim()
                val confirmPassword = confirmPasswordEt.editText?.text.toString().trim()

                if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && phone.isNotEmpty()) {
                    val request = SignUpRequest(username, email, phone, password, confirmPassword)
                    signUpViewModel.signUp(request)
                }
            }
            alreadyHaveAccountTv.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }

        }

        signUpViewModel.signUpResponse.observe(viewLifecycleOwner, Observer { signUpResponse ->
            when (signUpResponse.message) {
                "Added Successfully" -> {
                    dataStoreViewModel.saveUserName(signUpResponse.savedUser.userName)
                    findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                }
                "Validation error" -> {
                    Toast.makeText(
                        context,
                        "Not matching pattern",
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}