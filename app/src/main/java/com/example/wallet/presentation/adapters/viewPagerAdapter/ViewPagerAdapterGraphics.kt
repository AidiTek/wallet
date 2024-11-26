package com.example.wallet.presentation.adapters.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wallet.presentation.fragments.fragmentsDaysOnGraphics.FragmentDayGraphics
import com.example.wallet.presentation.fragments.fragmentsDaysOnGraphics.FragmentMonthGraphics
import com.example.wallet.presentation.fragments.fragmentsDaysOnGraphics.FragmentWeekGraphics
import com.example.wallet.presentation.fragments.fragmentsDaysOnGraphics.FragmentYearGraphics

class ViewPagerAdapterGraphics(activity:FragmentActivity):FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
    return when(position){
        0->FragmentDayGraphics()
        1->FragmentWeekGraphics()
        2->FragmentMonthGraphics()
        3->FragmentYearGraphics()
        else -> throw IllegalStateException("Invalid position")
    }
    }
}