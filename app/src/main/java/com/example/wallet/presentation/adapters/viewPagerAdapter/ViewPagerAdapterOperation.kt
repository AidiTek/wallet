package com.example.wallet.presentation.adapters.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wallet.presentation.fragments.fragmentsDaysPeriudsOperation.fragmentDay
import com.example.wallet.presentation.fragments.fragmentsDaysPeriudsOperation.fragmentMonth
import com.example.wallet.presentation.fragments.fragmentsDaysPeriudsOperation.fragmentWekk
import com.example.wallet.presentation.fragments.fragmentsDaysPeriudsOperation.fragmentsYear

class ViewPagerAdapterOperation(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragmentDay()
            1 -> fragmentWekk()
            2 -> fragmentMonth()
            3 -> fragmentsYear()
            else -> fragmentDay()
        }
    }

}