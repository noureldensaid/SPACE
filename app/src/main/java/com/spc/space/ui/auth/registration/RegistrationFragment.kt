package com.spc.space.ui.auth.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.spc.space.R
import com.spc.space.adapters.ViewPagerAdapter
import com.spc.space.databinding.FragmentRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegistrationBinding.bind(view)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        binding.apply {
            val list = arrayListOf(
                LoginFragment(),
                SignUpFragment()
            )
            val adapter = ViewPagerAdapter(list, childFragmentManager, lifecycle)
            registrationViewPager.adapter = adapter
            TabLayoutMediator(tabLayout, registrationViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Login"
                    1 -> tab.text = "Sign-up"
                }
            }.attach()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}