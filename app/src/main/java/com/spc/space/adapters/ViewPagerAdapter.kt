package com.spc.space.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spc.space.ui.main.bookings.BookingsHistoryFragment
import com.spc.space.ui.main.bookings.CanceledBookingsFragment
import com.spc.space.ui.main.bookings.UpcomingBookingsFragment


class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                UpcomingBookingsFragment()
            }
            1 -> {
                CanceledBookingsFragment()
            }
            else -> {
                BookingsHistoryFragment()
            }

        }
    }
}
