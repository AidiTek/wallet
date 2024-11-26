package com.example.wallet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.wallet.R
import com.example.wallet.databinding.FragmentGraphicsFragmnetBinding
import com.example.wallet.presentation.activity.MainActivity
import com.example.wallet.presentation.adapters.viewPagerAdapter.ViewPagerAdapterGraphics
import com.google.android.material.tabs.TabLayoutMediator

class graphicsFragmnet : Fragment() {

    private var _binding: FragmentGraphicsFragmnetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGraphicsFragmnetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapterGraphics(requireActivity())
        binding.viewPagerGraphics.adapter = adapter
        binding.viewPagerGraphics.isUserInputEnabled = false

        val mainActivity = requireActivity() as MainActivity

        mainActivity.findViewById<ImageView>(R.id.money_bag).apply {
            visibility = View.GONE
        }
        mainActivity.findViewById<ImageView>(R.id.iv_changeInvoice).apply {
            visibility = View.GONE
        }
        mainActivity.findViewById<ImageView>(R.id.iv_coin).apply {
            visibility = View.GONE
        }
        mainActivity.findViewById<TextView>(R.id.tv_moneyCount).apply {
            visibility = View.GONE
        }
        mainActivity.findViewById<ImageView>(R.id.iv_leftIcon).apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.side_navigation_24dp)
        }
        mainActivity.findViewById<ImageView>(R.id.iv_rightIcon).apply {
            visibility = View.GONE
            setImageResource(R.drawable.search_24dp_icon)
        }
        val toolbarTitle = requireActivity().findViewById<TextView>(R.id.tv_invoice)
        toolbarTitle.text = "График"


        TabLayoutMediator(binding.tabLayoutGraphics, binding.viewPagerGraphics) { tab, position ->

            tab.text = when (position) {
                0 -> "День"
                1 -> "Неделя"
                2 -> "Месяц"
                3 -> "Год"
                else -> "День"
            }
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}