package com.example.wallet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.wallet.R
import com.example.wallet.databinding.FragmentAllOperatonBinding
import com.example.wallet.presentation.activity.MainActivity
import com.example.wallet.presentation.adapters.viewPagerAdapter.ViewPagerAdapterOperation
import com.example.wallet.presentation.custom_view.MultiSectionBar
import com.example.wallet.presentation.data.Section
import com.google.android.material.tabs.TabLayoutMediator

class allOperatonFragment : Fragment() {

    private var _binding: FragmentAllOperatonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllOperatonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = requireActivity() as MainActivity
        val multiSectionBar = requireActivity().findViewById<MultiSectionBar>(R.id.multiSectionBaralloperation)

        mainActivity.findViewById<ImageView>(R.id.iv_rightIcon).apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.search_24dp_icon)
        }
        mainActivity.findViewById<ImageView>(R.id.money_bag).visibility = View.GONE
        mainActivity.findViewById<ImageView>(R.id.iv_changeInvoice).visibility = View.GONE
        val toolbarTitle = requireActivity().findViewById<TextView>(R.id.tv_invoice)
        toolbarTitle.text = "Операций"


        val section = listOf(
            Section(
                percentage = 0.3f,
                color = ContextCompat.getColor(requireContext(), R.color.cafeBlue)
            ),
            Section(
                percentage = 0.2f,
                color = ContextCompat.getColor(requireContext(), R.color.homeOrange)
            ),
            Section(
                percentage = 0.5f,
                color = ContextCompat.getColor(requireContext(), R.color.leisureGreen)
            ),
        )
        multiSectionBar.setSection(section)

        val viewPagerAdapter = ViewPagerAdapterOperation(requireActivity())



        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.isUserInputEnabled = false

        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position ->

            tab.text = when(position){
                0 -> "День"
                1 -> "Неделя"
                2 -> "Месяц"
                3 -> "Год"
                else -> "День"
            }
        }.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}