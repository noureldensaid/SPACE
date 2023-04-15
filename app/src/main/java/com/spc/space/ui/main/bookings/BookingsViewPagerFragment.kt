package com.spc.space.ui.main.bookings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.spc.space.R
import com.spc.space.adapters.ViewPagerAdapter
 import com.spc.space.databinding.FragmentBookingsViewpagerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingsViewPagerFragment : Fragment(R.layout.fragment_bookings_viewpager) {
    private var _binding: FragmentBookingsViewpagerBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookingsViewpagerBinding.bind(view)
        setUpViewPager()

    }

    private fun setUpViewPager() {
        val list = arrayListOf(
            UpcomingBookingsFragment(),
            CanceledBookingsFragment(),
            BookingsHistoryFragment()
        )
        binding.apply {
            val adapter = ViewPagerAdapter(list, childFragmentManager, lifecycle)
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