package com.example.wallet.presentation.adapters.rvAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.data.rvdata.CategoryItem
import com.example.wallet.data.rvdata.allOerationData
import com.example.wallet.databinding.FragmentYearGraphicsBinding
import com.example.wallet.databinding.ItemsInAlloperationfragmentBinding
import com.example.wallet.presentation.viewModel.OperationViewModel

class OperationAdapter(
    private var operations: List<CategoryItem>
) : RecyclerView.Adapter<OperationAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemsInAlloperationfragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(operation: CategoryItem, isLastItem: Boolean) {
            binding.ivIcons.setImageResource(operation.iconeResId)
            binding.tvTitle.text = operation.title
            binding.tvMoneyCount.text = operation.amount

            // Скрываем разделитель, если это последний элемент
            if (isLastItem) {
                binding.bottomDivider.visibility = View.GONE
            } else {
                binding.bottomDivider.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsInAlloperationfragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = operations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isLastItem = position == operations.size - 1  // Определяем, является ли текущий элемент последним
        holder.onBind(operations[position], isLastItem)  // Передаем оба параметра в метод onBind
    }

    fun updateCategories(newCategories: List<CategoryItem>){
        operations = newCategories
        notifyDataSetChanged()
    }
}
