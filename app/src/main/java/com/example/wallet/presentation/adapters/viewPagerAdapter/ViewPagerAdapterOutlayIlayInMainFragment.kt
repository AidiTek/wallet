package com.example.wallet.presentation.adapters.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wallet.presentation.fragments.fragmentsOutlayAndInlay.fragmentInlayInMainFragment
import com.example.wallet.presentation.fragments.fragmentsOutlayAndInlay.fragmentOutlayInMainFragment

class ViewPagerAdapterOutlayIlayInMainFragment(activity:FragmentActivity):FragmentStateAdapter(activity) {
    override fun getItemCount(): Int  =2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->fragmentInlayInMainFragment()
            1->fragmentOutlayInMainFragment()
            else -> throw IllegalStateException("Invalid Position")
        }
    }
}