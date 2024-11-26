package com.example.wallet.presentation.fragments.fragmentsOutlayAndInlay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.data.rvdata.CategoryItem
import com.example.wallet.databinding.FragmentInlayInMainBinding
import com.example.wallet.presentation.adapters.rvAdapter.CategoryAdapter
import com.example.wallet.presentation.custom.customeAnimationRV.CustomeItemAnimator
import com.example.wallet.presentation.viewModel.OperationViewModel
import com.example.wallet.presentation.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class fragmentInlayInMainFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val operationViewModel: OperationViewModel by activityViewModels()

    private var _binding: FragmentInlayInMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInlayInMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoryAdapter(mutableListOf(), operationViewModel, requireContext())
        binding.rvInmainfragment.adapter = adapter
        binding.rvInmainfragment.itemAnimator = CustomeItemAnimator()

        sharedViewModel.selectedAccountId.observe(viewLifecycleOwner) { accountId ->
            if (accountId != null) {
                operationViewModel.getOperationsByAccount(accountId).observe(viewLifecycleOwner) { incomeList ->
                    val categoryItems = incomeList.map {
                        CategoryItem(
                            operation = it,
                            iconeResId = it.iconeResId, // Иконка для доходов
                            title = it.category,
                            persentage = "49%", // Здесь может быть ваша логика
                            amount = it.amount.toString()
                        )
                    }
                    adapter.updateCategories(categoryItems)
                }
            }
        }

        binding.rvInmainfragment.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
