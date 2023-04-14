package com.spc.space.ui.auth.onboarding.welcomeScreens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.adapters.ViewPagerAdapter
import com.spc.space.databinding.FragmentWelcomeViewpagerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeViewPagerFragment : Fragment(R.layout.fragment_welcome_viewpager) {
    private var _binding: FragmentWelcomeViewpagerBinding? = null
    private val binding get() = _binding!!
    lateinit var welcomeViewPagerAdapter: ViewPagerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWelcomeViewpagerBinding.bind(view)

        setUpViewPager()

        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeViewPagerFragment_to_registrationFragment)
        }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}