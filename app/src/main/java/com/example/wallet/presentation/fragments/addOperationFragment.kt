package com.example.wallet.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentAddOperationBinding
import com.example.wallet.presentation.activity.MainActivity
import com.example.wallet.presentation.adapters.viewPagerAdapter.ViewPagerAdapterOutlayIlayInAddFragment
import com.example.wallet.presentation.viewModel.OperationViewModel
import com.example.wallet.presentation.viewModel.SharedViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class addOperationFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: OperationViewModel by activityViewModels()

    private var _binding: FragmentAddOperationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddOperationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Настройка toolbar
        val mainActivity = requireActivity() as MainActivity
        setupToolbar(mainActivity)

        // Настройка ViewPager2 с TabLayout
        setupViewPagerWithTabs()

        binding.FABAddNewItem.setOnClickListener {
            addNewOperation()
        }
    }

    private fun setupToolbar(mainActivity: MainActivity) {
        mainActivity.findViewById<ImageView>(R.id.money_bag).visibility = View.GONE
        mainActivity.findViewById<ImageView>(R.id.iv_changeInvoice).visibility = View.GONE
        mainActivity.findViewById<ImageView>(R.id.iv_coin).visibility = View.GONE
        mainActivity.findViewById<TextView>(R.id.tv_moneyCount).visibility = View.GONE
        mainActivity.findViewById<ImageView>(R.id.iv_leftIcon).apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.arrow_back_24dp)
        }
        mainActivity.findViewById<ImageView>(R.id.iv_rightIcon).apply {
            visibility = View.GONE
            setImageResource(R.drawable.search_24dp_icon)
        }

        val toolbarTitle = mainActivity.findViewById<TextView>(R.id.tv_invoice)
        toolbarTitle.text = "Добавление операций"
    }

    private fun setupViewPagerWithTabs() {
        val adapter = ViewPagerAdapterOutlayIlayInAddFragment(requireActivity())
        binding.viewPagerOutLayInLay.adapter = adapter
        binding.viewPagerOutLayInLay.isUserInputEnabled = false

        TabLayoutMediator(
            binding.tabLayoutOutLayInLay,
            binding.viewPagerOutLayInLay
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Расходы"
                1 -> tab.text = "Доходы"
            }
        }.attach()
    }

    private fun addNewOperation() {
        // Получаем данные для новой операции
        val category = sharedViewModel.categoryInput.value ?: "Не выбрано"
        val amount = sharedViewModel.amountInput.value ?: 0
        val percentage = "49%"
        val isIncome = binding.tabLayoutOutLayInLay.selectedTabPosition == 1 // Если выбран доход
        val iconResId = sharedViewModel.selectedIcon.value
            ?: R.drawable.drawer_header_backgraund // Иконка по умолчанию

        // Получаем ID выбранного счета из SharedViewModel
        val accountID = sharedViewModel.getSelectedAccountId()
        if (accountID == null) {
            Toast.makeText(requireContext(), "Счет не выбран", Toast.LENGTH_SHORT).show()
            return
        }

        // Добавление операции в базу данных
        viewModel.addOperation(
            accountId = accountID,
            category = category,
            amount = amount,
            percentage = percentage,
            isIncome = isIncome,
            iconeResId = iconResId,
            date = System.currentTimeMillis()
        )

        // Сбрасываем значение введенной суммы
        sharedViewModel.amountInput.value = 0

        // Навигация обратно с небольшой задержкой
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigateUp()
        }, 100)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
