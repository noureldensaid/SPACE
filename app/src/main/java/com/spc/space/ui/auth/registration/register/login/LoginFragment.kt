package com.spc.space.ui.auth.registration.register.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentLoginBinding
import com.spc.space.models.auth.signIn.SignInRequest
import com.spc.space.ui.DataStoreViewModel
import com.spc.space.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val loginViewModel: LoginViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        loginViewModel.signInResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.message) {
                    "Welcome" -> {
                        dataStoreViewModel.saveToken(it.token)
                        startActivity(Intent(activity, MainActivity::class.java))
                        activity?.finish()
                    }
                    "you have to confirm email first" -> {
                        Toast.makeText(
                            context,
                            "Please Confirm your email first",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            context,
                            "Invalid Email or Password",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
        //  get sign up data the wrap it in sign up request
        binding.apply {
            signInBtn.setOnClickListener {
                val email = emailEt.editText?.text.toString().trim()
                val username = usernameEt.editText?.text.toString().trim()
                val password = passwordEt.editText?.text.toString().trim()
                if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                    val request = SignInRequest(email, password)
                    loginViewModel.signIn(request)
                    dataStoreViewModel.saveUserName(username)
                }
            }
        }





        binding.forgotPasswordTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}