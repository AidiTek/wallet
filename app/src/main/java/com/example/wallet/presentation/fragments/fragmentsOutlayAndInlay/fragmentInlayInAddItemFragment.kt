package com.example.wallet.presentation.fragments.fragmentsOutlayAndInlay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.example.wallet.R
import com.example.wallet.databinding.FragmentInlayInAddItemBinding
import com.example.wallet.presentation.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class fragmentInlayInAddItemFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentInlayInAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInlayInAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.ivLeisureInAddFromgentIn.setOnClickListener {
            sharedViewModel.categoryInput.value = "Досуг"
            sharedViewModel.selectedIcon.value = R.drawable.laiser_icone_in_addfragment24dp
            sharedViewModel.selectedCategoryPosition.value = 0
        }
        binding.ivCofeInAddFromgentIn.setOnClickListener {
            sharedViewModel.categoryInput.value = "Кафе"
            sharedViewModel.selectedIcon.value = R.drawable.fork_spoon_in_addfragment_24dp_
            sharedViewModel.selectedCategoryPosition.value = 1
        }
        binding.ivHouseInAddFromgentIn.setOnClickListener {
            sharedViewModel.categoryInput.value = "Дом"
            sharedViewModel.selectedIcon.value = R.drawable.house_24dp_in_addfragement
            sharedViewModel.selectedCategoryPosition.value = 2
        }
        binding.ivHeartInAddFromgentIn.setOnClickListener {
            sharedViewModel.categoryInput.value = "Здаровье"
            sharedViewModel.selectedIcon.value = R.drawable.heart_in_addfragment_24dp
            sharedViewModel.selectedCategoryPosition.value = 3
        }
        binding.ivEducationInAddFromgentIn.setOnClickListener {
            sharedViewModel.categoryInput.value = "Оброзавания"
            sharedViewModel.selectedIcon.value = R.drawable.education_in_addfragment
            sharedViewModel.selectedCategoryPosition.value = 4
        }
        binding.ivPresentInAddFromgentIn.setOnClickListener {
            sharedViewModel.categoryInput.value = "Подарки"
            sharedViewModel.selectedIcon.value = R.drawable.present_icone_in_adfragment_24dp
            sharedViewModel.selectedCategoryPosition.value = 5
        }
        binding.ivProductsInAddFromgentIn.setOnClickListener {
            sharedViewModel.categoryInput.value = "Продукты"
            sharedViewModel.selectedIcon.value = R.drawable.products_icone_24dp_inadfragment
            sharedViewModel.selectedCategoryPosition.value = 6
        }
        binding.ivFamilyInAddFromgentIn.setOnClickListener {
            sharedViewModel.categoryInput.value = "Семья"
            sharedViewModel.selectedIcon.value = R.drawable.famaly_icone_24dp_inadfragment
            sharedViewModel.selectedCategoryPosition.value = 7
        }
        binding.ivTransportInAddFromgentIn.setOnClickListener {
            sharedViewModel.categoryInput.value = "Транспорт"
            sharedViewModel.selectedIcon.value = R.drawable.transport_icon_bus_24dp
            sharedViewModel.selectedCategoryPosition.value = 8
        }
        binding.ivSportInAddFromgentIn.setOnClickListener {
            sharedViewModel.categoryInput.value = "Спорт"
            sharedViewModel.selectedIcon.value = R.drawable.sport_icon_24dp
            sharedViewModel.selectedCategoryPosition.value = 9
        }

        binding.etCountInAddFragment.doOnTextChanged { text,_,_,_ ->
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
        binding.ivLeisureInAddFromgentIn.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivCofeInAddFromgentIn.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivHouseInAddFromgentIn.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivHeartInAddFromgentIn.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivEducationInAddFromgentIn.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivPresentInAddFromgentIn.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivProductsInAddFromgentIn.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivFamilyInAddFromgentIn.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivTransportInAddFromgentIn.setBackgroundResource(R.color.mainBackgroundBlue)
        binding.ivSportInAddFromgentIn.setBackgroundResource(R.color.mainBackgroundBlue)
    }

    private fun highLightSelectedColor(position: Int) {

        when (position) {
            0 -> binding.ivLeisureInAddFromgentIn.setBackgroundResource(R.drawable.backgraund_choosed_image)
            1 -> binding.ivCofeInAddFromgentIn.setBackgroundResource(R.drawable.backgraund_choosed_image)
            2 -> binding.ivHouseInAddFromgentIn.setBackgroundResource(R.drawable.backgraund_choosed_image)
            3 -> binding.ivHeartInAddFromgentIn.setBackgroundResource(R.drawable.backgraund_choosed_image)
            4 -> binding.ivEducationInAddFromgentIn.setBackgroundResource(R.drawable.backgraund_choosed_image)
            5 -> binding.ivPresentInAddFromgentIn.setBackgroundResource(R.drawable.backgraund_choosed_image)
            6 -> binding.ivProductsInAddFromgentIn.setBackgroundResource(R.drawable.backgraund_choosed_image)
            7 -> binding.ivFamilyInAddFromgentIn.setBackgroundResource(R.drawable.backgraund_choosed_image)
            8 -> binding.ivTransportInAddFromgentIn.setBackgroundResource(R.drawable.backgraund_choosed_image)
            9 -> binding.ivSportInAddFromgentIn.setBackgroundResource(R.drawable.backgraund_choosed_image)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}