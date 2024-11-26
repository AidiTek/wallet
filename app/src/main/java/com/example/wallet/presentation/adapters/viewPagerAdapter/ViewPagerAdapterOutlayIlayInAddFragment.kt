package com.example.wallet.presentation.adapters.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wallet.presentation.fragments.fragmentsOutlayAndInlay.fragmentInlayInAddItemFragment
import com.example.wallet.presentation.fragments.fragmentsOutlayAndInlay.fragmentOutlayInAddItemFragment

class ViewPagerAdapterOutlayIlayInAddFragment(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragmentInlayInAddItemFragment()
            1 -> fragmentOutlayInAddItemFragment()
            else -> throw IllegalStateException("Invalid Possition")
        }
    }
}