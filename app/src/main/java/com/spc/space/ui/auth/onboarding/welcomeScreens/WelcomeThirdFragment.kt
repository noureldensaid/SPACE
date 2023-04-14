package com.spc.space.ui.auth.onboarding.welcomeScreens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentWelcomePlaceholderBinding
import com.spc.space.databinding.FragmentWelcomeThirdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class WelcomeThirdFragment : Fragment(R.layout.fragment_welcome_third) {
    private var _binding: FragmentWelcomeThirdBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWelcomeThirdBinding.bind(view)



    }

}