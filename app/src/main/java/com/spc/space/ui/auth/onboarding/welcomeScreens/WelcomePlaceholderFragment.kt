package com.spc.space.ui.auth.onboarding.welcomeScreens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.adapters.ViewPagerAdapter
import com.spc.space.databinding.FragmentWelcomePlaceholderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomePlaceholderFragment : Fragment(R.layout.fragment_welcome_placeholder) {
    private var _binding: FragmentWelcomePlaceholderBinding? = null
    private val binding get() = _binding!!
    lateinit var welcomeViewPagerAdapter: ViewPagerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWelcomePlaceholderBinding.bind(view)

        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_welcomePlaceholderFragment_to_registrationFragment)
        }

        setUpViewPager()

    }

    private fun setUpViewPager() {
        val list = arrayListOf(
            WelcomeFirstFragment(),
            WelcomeSecondFragment(),
            WelcomeThirdFragment()
        )
        val adapter = ViewPagerAdapter(list, childFragmentManager, lifecycle)
        binding.apply {
            viewPager.adapter = adapter
            dotsIndicator.attachTo(viewPager)
        }
    }
}