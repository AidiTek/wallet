package com.example.wallet.presentation.fragments.fragmentsOutlayAndInlay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.example.wallet.R
import com.example.wallet.databinding.FragmentInlayInMainBinding
import com.example.wallet.databinding.FragmentOutlayInAddItemBinding
import com.example.wallet.presentation.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class fragmentOutlayInAddItemFragment : Fragment() {
    private val sharedViewModel:SharedViewModel by activityViewModels()

    private var _binding: FragmentOutlayInAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOutlayInAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivLeisureInAddFromgent.setOnClickListener{
            sharedViewModel.categoryInput.value = "Досуг"
            sharedViewModel.outlauSelectedIcon.value = R.drawable.laiser_icone_in_addfragment24dp
            sharedViewModel.selectedCategoryPosition.value = 0
        }
        binding.ivCofeInAddFromgent.setOnClickListener{
            sharedViewModel.categoryInput.value = "Кафе"
            sharedViewModel.outlauSelectedIcon.value = R.drawable.fork_spoon_in_addfragment_24dp_
            sharedViewModel.selectedCategoryPosition.value = 1
        }
        binding.ivHouseInAddFromgent.setOnClickListener{
            sharedViewModel.categoryInput.value = "Дом"
            sharedViewModel.outlauSelectedIcon.value = R.drawable.house_24dp_in_addfragement
            sharedViewModel.selectedCategoryPosition.value = 2
        }
        binding.ivHeartInAddFromgent.setOnClickListener{
            sharedViewModel.categoryInput.value = "Здаровье"
            sharedViewModel.outlauSelectedIcon.value = R.drawable.heart_in_addfragment_24dp
            sharedViewModel.selectedCategoryPosition.value = 3
        }
        binding.ivEducationInAddFromgent.setOnClickListener{
            sharedViewModel.categoryInput.value = "Оброзавания"
            sharedViewModel.outlauSelectedIcon.value = R.drawable.education_in_addfragment
            sharedViewModel.selectedCategoryPosition.value = 4
        }
        binding.ivPresentInAddFromgent.setOnClickListener{
            sharedViewModel.categoryInput.value = "Подарки"
            sharedViewModel.outlauSelectedIcon.value = R.drawable.present_icone_in_adfragment_24dp
            sharedViewModel.selectedCategoryPosition.value = 5
        }
        binding.ivProductsInAddFromgent.setOnClickListener{
            sharedViewModel.categoryInput.value = "Продукты"
            sharedViewModel.outlauSelectedIcon.value = R.drawable.products_icone_24dp_inadfragment
            sharedViewModel.selectedCategoryPosition.value = 6
        }
        binding.ivFamilyInAddFromgent.setOnClickListener{
            sharedViewModel.categoryInput.value = "Семья"
            sharedViewModel.outlauSelectedIcon.value = R.drawable.famaly_icone_24dp_inadfragment
            sharedViewModel.selectedCategoryPosition.value = 7
        }
        binding.ivTransportInAddFromgent.setOnClickListener{
            sharedViewModel.categoryInput.value = "Транспорт"
            sharedViewModel.outlauSelectedIcon.value = R.drawable.transport_icon_bus_24dp
            sharedViewModel.selectedCategoryPosition.value = 8
        }
        binding.ivSportInAddFromgent.setOnClickListener{
            sharedViewModel.categoryInput.value = "Спорт"
            sharedViewModel.outlauSelectedIcon.value = R.drawable.sport_icon_24dp
            sharedViewModel.selectedCategoryPosition.value = 9
        }

        binding.etCountInAddFragment.doOnTextChanged{ text,_,_,_ ->
            val amount = text.toString().toIntOrNull() ?: 0
            sharedViewModel.amountInput.value = amount
        }

        observSelectedCategory()

    }

    private fun observSelectedCategory() {
        sharedViewModel.selectedCategoryPosition.observe(viewLifecycleOwner) { position ->
            resetCategoryBackgrands()
            highLightSelectedColor(position)

        }
    }

    private fun resetCategoryBackgrands() {
        binding.ivLeisureInAddFromgent.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivCofeInAddFromgent.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivHouseInAddFromgent.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivHeartInAddFromgent.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivEducationInAddFromgent.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivPresentInAddFromgent.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivProductsInAddFromgent.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivFamilyInAddFromgent.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivTransportInAddFromgent.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivSportInAddFromgent.setBackgroundResource(R.color.mainBackgroundBlue)
    }

    private fun highLightSelectedColor(position: Int) {

        when (position) {
            0 -> binding.ivLeisureInAddFromgent.setBackgroundResource(R.drawable.backgraund_choosed_image)
            1 -> binding.ivCofeInAddFromgent.setBackgroundResource(R.drawable.backgraund_choosed_image)
            2 -> binding.ivHouseInAddFromgent.setBackgroundResource(R.drawable.backgraund_choosed_image)
            3 -> binding.ivHeartInAddFromgent.setBackgroundResource(R.drawable.backgraund_choosed_image)
            4 -> binding.ivEducationInAddFromgent.setBackgroundResource(R.drawable.backgraund_choosed_image)
            5 -> binding.ivPresentInAddFromgent.setBackgroundResource(R.drawable.backgraund_choosed_image)
            6 -> binding.ivProductsInAddFromgent.setBackgroundResource(R.drawable.backgraund_choosed_image)
            7 -> binding.ivFamilyInAddFromgent.setBackgroundResource(R.drawable.backgraund_choosed_image)
            8 -> binding.ivTransportInAddFromgent.setBackgroundResource(R.drawable.backgraund_choosed_image)
            9 -> binding.ivSportInAddFromgent.setBackgroundResource(R.drawable.backgraund_choosed_image)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}