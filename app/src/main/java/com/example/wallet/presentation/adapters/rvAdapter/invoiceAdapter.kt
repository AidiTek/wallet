package com.example.wallet.presentation.adapters.rvAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.data.rvdata.InvoiseData
import com.example.wallet.databinding.ItemsInInvoicefragmentBinding

class invoiceAdapter (
    private var invoices: MutableList<InvoiseData>,
    private val onTotalChanged: (Int) -> Unit
    ): RecyclerView.Adapter<invoiceAdapter.ViewHolder>() {
    class ViewHolder(private  val binding: ItemsInInvoicefragmentBinding):RecyclerView.ViewHolder(binding.root) {
        fun onBing(invoise:InvoiseData){
            binding.tvTitle.text = invoise.title
            binding.tvMoneyCount.text = "${invoise.amount}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = ItemsInInvoicefragmentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = invoices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBing(invoices[position])
    }

    fun updateInvoices(newInvoices: List<InvoiseData>) {
        invoices.clear()
        invoices.addAll(newInvoices)
        notifyDataSetChanged()
        updateTotal()
    }

    fun addInvoice(newInvoice: InvoiseData) {
        invoices.add(newInvoice)
        notifyItemInserted(invoices.size - 1)
        updateTotal()
    }

    fun updateInvoice(position: Int, updatedInvoice: InvoiseData) {
        if (position in invoices.indices) {
            invoices[position] = updatedInvoice
            notifyItemChanged(position)
            updateTotal()
        }
    }

    fun removeInvoice(position: Int) {
        if (position in invoices.indices) {
            invoices.removeAt(position)
            notifyItemRemoved(position)
            updateTotal()
        }
    }

    private fun updateTotal() {
        val total = invoices.sumOf { it.amount }
        onTotalChanged(total)
    }

}