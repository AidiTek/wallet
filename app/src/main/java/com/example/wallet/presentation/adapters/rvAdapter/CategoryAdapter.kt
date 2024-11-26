package com.example.wallet.presentation.adapters.rvAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.R
import com.example.wallet.data.rvdata.CategoryItem
import com.example.wallet.databinding.ItemsInMainFragmentBinding
import com.example.wallet.presentation.viewModel.OperationViewModel

class CategoryAdapter(
    private var categories: MutableList<CategoryItem>,
    private val viewModel: OperationViewModel,
    private val context:Context
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemsInMainFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryItem) {
            binding.ivIcons.setImageResource(category.iconeResId)
            binding.tvTitle.text = category.title
            binding.tvPercent.text = category.persentage
            binding.tvMoneyCount.text = category.amount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemsInMainFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount() = categories.size

    // Метод для обновления данных
    fun updateCategories(newCategories: List<CategoryItem>) {
        categories.clear()
        categories.addAll(newCategories)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val curentItem = categories[position]
        holder.bind(curentItem)
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context,R.style.CustomAlertDialogThem)
                .setTitle("Удалить операцию")
                .setMessage("Вы уверены, что хотите удалить эту операцию")
                .setPositiveButton("Да"){_,_ ->
                    deletItem(curentItem,position)
                }
                .setNegativeButton("Нет",null)
                .show()
            true
        }
    }

    private fun deletItem(item: CategoryItem, position: Int) {
        categories.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,categories.size)

        viewModel.deleteOperation(item.operation)
    }
}
