package com.example.wallet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentMainBinding
import com.example.wallet.presentation.activity.MainActivity
import com.example.wallet.presentation.adapters.viewPagerAdapter.ViewPagerAdapterOutlayIlayInMainFragment
import com.example.wallet.presentation.custom_view.MultiSectionBar
import com.example.wallet.presentation.data.Section
import com.example.wallet.presentation.rvInterfaces.ScroleListener
import com.example.wallet.presentation.viewModel.SharedViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class mainFragment : Fragment() {

    val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var isFABfullyVissible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        updateMultiSectionbar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = requireActivity() as MainActivity

        mainActivity.findViewById<ImageView>(R.id.iv_coin).visibility = View.VISIBLE
        mainActivity.findViewById<TextView>(R.id.tv_moneyCount).visibility = View.VISIBLE
        mainActivity.findViewById<ImageView>(R.id.money_bag).visibility = View.VISIBLE
        mainActivity.findViewById<ImageView>(R.id.iv_changeInvoice).visibility = View.VISIBLE
        mainActivity.findViewById<ImageView>(R.id.iv_rightIcon).apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.receipt_long_24dp)
        }
        mainActivity.findViewById<ImageView>(R.id.iv_leftIcon).apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.side_navigation_24dp)
        }

        val toolbarTitle = requireActivity().findViewById<TextView>(R.id.tv_invoice)
        toolbarTitle.text = "Счет"

        sharedViewModel.isScroled.observe(viewLifecycleOwner) { scroled ->
            if (scroled && isFABfullyVissible) {
                binding.FAB.alpha = 0.6f
                isFABfullyVissible = false
            }
        }

        binding.FAB.setOnClickListener {
            if (!isFABfullyVissible) {
                binding.FAB.alpha = 1.0f
                isFABfullyVissible = true
            } else {
                findNavController().navigate(R.id.action_mainFragment_to_addOperationFragment)
            }
        }
    }

    private fun updateMultiSectionbar() {

        val multiSectionBar = requireActivity().findViewById<MultiSectionBar>(R.id.multiSectionBar)
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

        val adapter = ViewPagerAdapterOutlayIlayInMainFragment(requireActivity())
        binding.viewPagerOutLayInLay.adapter = adapter
        binding.viewPagerOutLayInLay.isUserInputEnabled = false

        TabLayoutMediator(
            binding.tabLayoutOutLayInLay,
            binding.viewPagerOutLayInLay
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Расходы"
                1 -> tab.text = "Даходы"
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
