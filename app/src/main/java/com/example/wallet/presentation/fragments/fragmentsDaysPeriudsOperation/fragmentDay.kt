package com.example.wallet.presentation.fragments.fragmentsDaysPeriudsOperation

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.wallet.R
import com.example.wallet.data.rvdata.CategoryItem
import com.example.wallet.data.rvdata.allOerationData
import com.example.wallet.databinding.FragmentDayOperationBinding
import com.example.wallet.presentation.adapters.rvAdapter.CategoryAdapter
import com.example.wallet.presentation.adapters.rvAdapter.OperationAdapter
import com.example.wallet.presentation.custom.customeAnimationRV.CustomeItemAnimator
import com.example.wallet.presentation.viewModel.OperationViewModel
import com.example.wallet.presentation.viewModel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.cancellation.CancellationException

class fragmentDay : Fragment() {

    private val operationViewModel:OperationViewModel by activityViewModels()

    private var _binding: FragmentDayOperationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayOperationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toDayDat =SimpleDateFormat("dd MMMM", Locale.getDefault()).format(Date())
        binding.tvOperationDateDate.text = toDayDat

        val adapter = OperationAdapter(mutableListOf())
        binding.recyclerViewDay.adapter = adapter
        binding.recyclerViewDay.itemAnimator = CustomeItemAnimator()

        operationViewModel.incomeOperation.observe(viewLifecycleOwner){ allOperations->

            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY,0)
                set(Calendar.MINUTE,0)
                set(Calendar.SECOND,0)
                set(Calendar.MILLISECOND,0)
            }.timeInMillis

            val todayOperations = allOperations.filter { it.date >= today }

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}