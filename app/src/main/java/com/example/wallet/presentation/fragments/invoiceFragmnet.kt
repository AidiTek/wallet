package com.example.wallet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.wallet.R
import com.example.wallet.data.entity.OperationEntity
import com.example.wallet.data.rvdata.InvoiseData
import com.example.wallet.databinding.FragmentInvoiceFragmnetBinding
import com.example.wallet.presentation.activity.MainActivity
import com.example.wallet.presentation.adapters.rvAdapter.invoiceAdapter
import com.example.wallet.presentation.app.App
import com.example.wallet.presentation.viewModel.SharedViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class invoiceFragmnet : Fragment() {

    private var _binding: FragmentInvoiceFragmnetBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceFragmnetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolBar()

        val adapter = invoiceAdapter(mutableListOf()) { newTotal ->
            binding.tvTotalAmount.text = "$newTotal"
        }
        binding.recyclerView.adapter = adapter

        sharedViewModel.allAccounts.observe(viewLifecycleOwner) { accounts ->
            val invoiceList = accounts.map { account ->
                InvoiseData(
                    title = account.category,
                    amount = account.amount
                )
            }
            adapter.updateInvoices(invoiceList)
        }

        binding.FAB.setOnClickListener {
            showAddAccountDialog(adapter)
        }

    }

    private fun setUpToolBar() {
        val mainActivity = requireActivity() as MainActivity

        mainActivity.findViewById<ImageView>(R.id.money_bag).visibility = View.GONE
        mainActivity.findViewById<ImageView>(R.id.iv_changeInvoice).visibility = View.GONE
        mainActivity.findViewById<ImageView>(R.id.iv_coin).visibility = View.GONE
        mainActivity.findViewById<TextView>(R.id.tv_moneyCount).visibility = View.GONE
        mainActivity.findViewById<ImageView>(R.id.iv_leftIcon).apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.side_navigation_24dp)
        }
        mainActivity.findViewById<ImageView>(R.id.iv_rightIcon).apply {
            visibility = View.GONE
            setImageResource(R.drawable.search_24dp_icon)
        }
        val toolbarTitle = requireActivity().findViewById<TextView>(R.id.tv_invoice)
        toolbarTitle.text = "Счета"
    }

    private fun showAddAccountDialog(adapter: invoiceAdapter) {

        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_account, null)
        val accountName = dialogView.findViewById<EditText>(R.id.et_accountName)
        val accountAmount = dialogView.findViewById<EditText>(R.id.et_acountAmount)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialogThem)
            .setTitle("Добавить новый счет")
            .setView(dialogView)
            .setPositiveButton("Добавить") { _, _ ->

                val accountName = accountName.text.toString()
                val accountMaunt = accountAmount.text.toString().toIntOrNull() ?: 0

                if (accountName.isNotEmpty()) {
                    sharedViewModel.addAccount(
                        title = accountName,
                        amount = accountMaunt
                    )
                } else {
                    Toast.makeText(requireContext(), "Введите название счета", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Отмена", null)
            .create()

        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}