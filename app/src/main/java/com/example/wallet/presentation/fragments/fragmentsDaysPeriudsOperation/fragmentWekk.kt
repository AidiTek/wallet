package com.example.wallet.presentation.fragments.fragmentsDaysPeriudsOperation

import android.icu.util.Calendar
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
import com.example.wallet.databinding.FragmentWekkOperationBinding
import com.example.wallet.presentation.adapters.rvAdapter.CategoryAdapter
import com.example.wallet.presentation.adapters.rvAdapter.OperationAdapter
import com.example.wallet.presentation.custom.customeAnimationRV.CustomeItemAnimator
import com.example.wallet.presentation.viewModel.OperationViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class fragmentWekk : Fragment() {

    private val operationViewModel: OperationViewModel by activityViewModels()

    private var _binding: FragmentWekkOperationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWekkOperationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OperationAdapter(mutableListOf())
        binding.recyclerViewWekk.adapter = adapter
        binding.recyclerViewWekk.itemAnimator = CustomeItemAnimator()

        val calendarStart = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startWekkMilis = calendarStart.timeInMillis

        val calendarEnd = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            add(Calendar.DAY_OF_MONTH, 6)
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        val endWekkMilis = calendarEnd.timeInMillis

        val dateFormate = SimpleDateFormat("dd MMMM", Locale.getDefault())
        val weekRange = "${dateFormate.format(startWekkMilis)} ... ${dateFormate.format(endWekkMilis)}"
        binding.tvOperationDateWekk.text = weekRange


        operationViewModel.incomeOperation.observe(viewLifecycleOwner) { allOperations ->
           val weekOperations = allOperations.filter {
               it.date in startWekkMilis..endWekkMilis
           }
            val categoryItems = weekOperations.map {
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