package com.spc.space.ui.main.bookings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.spc.space.R
import com.spc.space.adapters.ViewPagerAdapter
import com.spc.space.databinding.FragmentBookingsBinding
import com.spc.space.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingsFragment : Fragment(R.layout.fragment_bookings) {
    private var _binding: FragmentBookingsBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookingsBinding.bind(view)
        setUpViewPager(childFragmentManager,lifecycle)

    }

    private fun setUpViewPager(fragmentManager: FragmentManager, lifecycle: Lifecycle) {
        binding.apply {
            val adapter = ViewPagerAdapter(fragmentManager, lifecycle)
            bookingsViewPager.adapter = adapter
            TabLayoutMediator(tabLayout, bookingsViewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Upcoming"
                    }
                    1 -> {
                        tab.text = "Canceled"
                    }
                    2 -> {
                        tab.text = "History"
                    }
                }
            }.attach()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}