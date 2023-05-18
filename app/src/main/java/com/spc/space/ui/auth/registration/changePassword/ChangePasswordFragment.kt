package com.spc.space.ui.auth.registration.changePassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentChangePasswordBinding
import com.spc.space.models.auth.changePassword.ChangePasswordRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private val changePasswordViewModel: ChangePasswordViewModel by viewModels()
    var otpCode: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChangePasswordBinding.bind(view)



        val otpEditTexts = arrayOf(
            binding.otpEditText1, binding.otpEditText2, binding.otpEditText3,
            binding.otpEditText4, binding.otpEditText5, binding.otpEditText6
        )

        for (i in 0 until otpEditTexts.size) {
            otpEditTexts[i].addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.length == 1) {
                        otpEditTexts[i].clearFocus()
                        if (i < otpEditTexts.size - 1) {
                            otpEditTexts[i + 1].requestFocus()
                            otpEditTexts[i + 1].setCursorVisible(true)
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                    if (s.length == 0 && i > 0) {
                        otpEditTexts[i - 1].requestFocus()
                    }
                    //for validation in otp
                    val enteredOtp = StringBuilder()
                    for (editText in otpEditTexts) {
                        enteredOtp.append(editText.text.toString())
                    }
                    if (isValidOtp(enteredOtp.toString())) {
                        setVisibilityChangePassData()
                        setVisibilityOtpGone()
                        otpCode= enteredOtp.toString()
                        Log.e("OTPCode", "afterTextChanged: $otpCode" )

                    }
                }
            })
        }

        changePasswordViewModel.changePasswordResponse.observe(viewLifecycleOwner, Observer { changePasswordResponse ->
            when (changePasswordResponse.message) {
                "Success" -> {
                    findNavController().navigate(R.id.action_changePasswordFragment_to_successNewPassFragment)
                }
                "Email or code is not valid" -> {
                    Toast.makeText(
                        context,
                        "Try Again ,Email or code is not valid",
                        Toast.LENGTH_LONG
                    ).show()

                    findNavController().navigate(R.id.action_changePasswordFragment_to_failedChangePasswordFragment)
                }

                "Validation error" -> {
                    Toast.makeText(
                        context,
                        "Try Again , verification code not correct",
                        Toast.LENGTH_LONG
                    ).show()

                    findNavController().navigate(R.id.action_changePasswordFragment_to_failedChangePasswordFragment)
                }



                else -> {
                    Toast.makeText(
                        context,
                        "Invalid Inputs",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_changePasswordFragment_to_failedChangePasswordFragment)
                }
            }
        })




        binding.btnResetPassword.setOnClickListener {
            val email = binding.editTextEmail.editText?.text.toString().trim()
            val newPassword = binding.editTextNewPassword.editText?.text.toString().trim()
            val request = ChangePasswordRequest(email, newPassword,otpCode)
            changePasswordViewModel.changePassword(request)
        }


    }
    fun isValidOtp(otp: String): Boolean {

        return otp.length == 6
    }

    fun setVisibilityChangePassData(){
        binding.newPasswordTv.visibility=View.VISIBLE
        binding.enterNewPassTv.visibility=View.VISIBLE
        binding.editTextEmail.visibility=View.VISIBLE
        binding.editTextEmail2.visibility=View.VISIBLE
        binding.editTextNewPassword.visibility=View.VISIBLE
        binding.editTextNewPassword2.visibility=View.VISIBLE
        binding.editTextNewPasswordAgain.visibility=View.VISIBLE
        binding.editTextNewPasswordAgain2.visibility=View.VISIBLE
        binding.btnResetPassword.visibility=View.VISIBLE
    }

    fun setVisibilityOtpGone(){
        binding.tvEnterVerrificationCode.visibility=View.GONE
        binding.linearLayoutOtp.visibility=View.GONE
    }



}