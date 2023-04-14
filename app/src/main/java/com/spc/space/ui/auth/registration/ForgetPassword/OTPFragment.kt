package com.spc.space.ui.auth.registration.ForgetPassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentOTPBinding

class OTPFragment : Fragment(R.layout.fragment_o_t_p) {
    private var _binding: FragmentOTPBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOTPBinding.bind(view)


//
//        binding.otpEditText1.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (binding.otpEditText1.text.toString().length == 1) {
//                    binding.otpEditText1.clearFocus()
//                    binding.otpEditText2.requestFocus()
//                    binding.otpEditText2.setCursorVisible(true)
//                }
//            }
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                if (binding.otpEditText1.text.toString().length == 0) {
//                    binding.otpEditText1.requestFocus()
//                }
//            }
//        })
//
//        binding.otpEditText2.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (binding.otpEditText2.text.toString().length == 1) {
//                    binding.otpEditText2.clearFocus()
//                    binding.otpEditText3.requestFocus()
//                    binding.otpEditText3.setCursorVisible(true)
//
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                if (binding.otpEditText2.text.toString().length == 0) {
//                    binding.otpEditText2.requestFocus()
//                }
//            }
//        })
//
//        binding.otpEditText3.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (binding.otpEditText3.text.toString().length == 1) {
//                    binding.otpEditText3.clearFocus()
//                    binding.otpEditText4.requestFocus()
//                    binding.otpEditText4.setCursorVisible(true)
//
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                if (binding.otpEditText3.text.toString().length == 0) {
//                    binding.otpEditText3.requestFocus()
//                }
//            }
//        })
//
//        binding.otpEditText4.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (binding.otpEditText4.text.toString().length == 1) {
//                    binding.otpEditText4.clearFocus()
//                    binding.otpEditText5.requestFocus()
//                    binding.otpEditText5.setCursorVisible(true)
//
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                if (binding.otpEditText4.text.toString().length == 0) {
//                    binding.otpEditText4.requestFocus()
//                }
//            }
//        })
//
//        binding.otpEditText5.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (binding.otpEditText5.text.toString().length == 1) {
//                    binding.otpEditText5.clearFocus()
//                    binding.otpEditText6.requestFocus()
//                    binding.otpEditText6.setCursorVisible(true)
//
//                }
//            }
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                if (binding.otpEditText5.text.toString().length == 0) {
//                    binding.otpEditText5.requestFocus()
//                }
//            }
//        })

        val otpEditTexts = arrayOf(binding.otpEditText1, binding.otpEditText2, binding.otpEditText3,
            binding.otpEditText4, binding.otpEditText5, binding.otpEditText6)

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

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
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
                    if (isValidOtp(enteredOtp.toString())){
                        findNavController().navigate(R.id.action_OTPFragment_to_changePasswordFragment)
                    }

                }
            })
        }



    }
    fun isValidOtp(otp: String): Boolean {

        return otp.length == 6
    }
}