package com.example.wallet.presentation.fragments.fragmentsDaysPeriudsOperation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.wallet.R
import com.example.wallet.data.rvdata.CategoryItem
import com.example.wallet.data.rvdata.allOerationData
import com.example.wallet.databinding.FragmentFragmentsYearOperationBinding
import com.example.wallet.databinding.FragmentMonthOperationBinding
import com.example.wallet.presentation.adapters.rvAdapter.OperationAdapter
import com.example.wallet.presentation.custom.customeAnimationRV.CustomeItemAnimator
import com.example.wallet.presentation.viewModel.OperationViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class fragmentsYear : Fragment() {

    private val operationViewModel: OperationViewModel by activityViewModels()

    private var _binding: FragmentFragmentsYearOperationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFragmentsYearOperationBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val yearDate = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())
        binding.tvOperationDateYear.text = yearDate

        val adapter = OperationAdapter(mutableListOf())
        binding.recyclerViewYear.adapter = adapter
        binding.recyclerViewYear.itemAnimator = CustomeItemAnimator()

        operationViewModel.incomeOperation.observe(viewLifecycleOwner) { allOperations ->

            // Установка начала текущей недели (например, с понедельника)
            val startOfWeek = Calendar.getInstance().apply {
                set(Calendar.MONTH,Calendar.JANUARY)
                set(Calendar.DAY_OF_YEAR, Calendar.MONDAY)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            // Фильтрация операций за текущую неделю
            val weeklyOperations = allOperations.filter { it.date >= startOfWeek }

            val categoryItems = weeklyOperations.map {
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}