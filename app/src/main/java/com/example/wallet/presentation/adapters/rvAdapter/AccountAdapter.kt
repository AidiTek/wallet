package com.example.wallet.presentation.adapters.rvAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.wallet.data.entity.OperationEntity
import com.example.wallet.databinding.ItemAccountBinding
import com.example.wallet.databinding.ItemsInInvoicefragmentBinding

class AccountAdapter(
    private val accounts: List<OperationEntity>,
    private val onAccountSelected: (OperationEntity) -> Unit
) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(account: OperationEntity, onAccountSelected: (OperationEntity) -> Unit) {
            binding.tvAccountName.text = account.category
            binding.tvAccountAmount.text = "${account.amount}"

            // Обработка нажатия на элемент
            binding.root.setOnClickListener {
                onAccountSelected(account)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAccountBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(accounts[position], onAccountSelected)
    }

    override fun getItemCount(): Int = accounts.size
}