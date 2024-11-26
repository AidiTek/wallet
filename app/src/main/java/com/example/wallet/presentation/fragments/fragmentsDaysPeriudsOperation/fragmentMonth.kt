package com.example.wallet.presentation.fragments.fragmentsDaysPeriudsOperation

import android.icu.util.Calendar
import android.icu.util.LocaleData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.wallet.R
import com.example.wallet.data.rvdata.CategoryItem
import com.example.wallet.data.rvdata.allOerationData
import com.example.wallet.databinding.FragmentMonthGraphicsBinding
import com.example.wallet.databinding.FragmentMonthOperationBinding
import com.example.wallet.presentation.adapters.rvAdapter.OperationAdapter
import com.example.wallet.presentation.custom.customeAnimationRV.CustomeItemAnimator
import com.example.wallet.presentation.viewModel.OperationViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

class fragmentMonth : Fragment() {

    private val operationViewModel: OperationViewModel by activityViewModels()

    private var _binding: FragmentMonthOperationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMonthOperationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val curentMonth = getMonthInNominativeCase()
        binding.tvOperationDateMonth.text = curentMonth

        val adapter = OperationAdapter(mutableListOf())
        binding.recyclerViewMonth.adapter = adapter
        binding.recyclerViewMonth.itemAnimator = CustomeItemAnimator()

        operationViewModel.incomeOperation.observe(viewLifecycleOwner) { allOperations ->
            val Wekk = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val todayOperations = allOperations.filter { it.date >= Wekk }

            val categoryItems = todayOperations.map {
                CategoryItem(
                    operation = it,
                    iconeResId = it.iconeResId,
                    title = it.category,
                    persentage = "49%",
                    amount = it.amount.toString()
                )
            }
            adapter.updateCategories(categoryItems)
        }
    }

    private fun getMonthInNominativeCase(): String {
        val months = arrayOf(
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        )
        val calender = Calendar.getInstance()
        val monthIndex = calender.get(Calendar.MONTH)
        return months[monthIndex]
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}