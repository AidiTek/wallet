package com.example.wallet.presentation.fragments.fragmentsOutlayAndInlay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.data.rvdata.CategoryItem
import com.example.wallet.databinding.FragmentOutlayInMainBinding
import com.example.wallet.presentation.adapters.rvAdapter.CategoryAdapter
import com.example.wallet.presentation.custom.customeAnimationRV.CustomeItemAnimator
import com.example.wallet.presentation.viewModel.OperationViewModel
import com.example.wallet.presentation.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class fragmentOutlayInMainFragment : Fragment() {

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val operationViewModel: OperationViewModel by activityViewModels()

    private var _binding: FragmentOutlayInMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOutlayInMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoryAdapter(mutableListOf(),operationViewModel,requireContext())
        binding.rvItem.adapter = adapter
        binding.rvItem.itemAnimator = CustomeItemAnimator()



        sharedViewModel.selectedAccountId.observe(viewLifecycleOwner){accountID ->
            if (accountID != null){
                operationViewModel.getOperationsByAccount(accountID).observe(viewLifecycleOwner) { expenseList ->
                    // Преобразование данных о расходах в CategoryItem
                    val categoryItems = expenseList.map {
                        CategoryItem(
                            operation = it,
                            iconeResId = it.iconeResId, // Иконка для расходов
                            title = it.category,
                            persentage = "49%", // Здесь может быть ваша логика для процента
                            amount = it.amount.toString()
                        )
                    }
                    // Обновление данных в адаптере
                    adapter.updateCategories(categoryItems)
                }
            }
        }


        binding.rvItem.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                sharedViewModel.isScroled.value = true
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}