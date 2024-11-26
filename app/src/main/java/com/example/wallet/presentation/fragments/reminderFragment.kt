package com.example.wallet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.wallet.R
import com.example.wallet.databinding.FragmentReminderBinding
import com.example.wallet.presentation.activity.MainActivity

class reminderFragment : Fragment() {

    private var _binding  : FragmentReminderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReminderBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = requireActivity() as MainActivity

        mainActivity.findViewById<ImageView>(R.id.money_bag).apply {
            visibility = View.GONE
        }
        mainActivity.findViewById<ImageView>(R.id.iv_changeInvoice).apply {
            visibility = View.GONE
        }
        mainActivity.findViewById<ImageView>(R.id.iv_coin).apply {
            visibility = View.GONE
        }
        mainActivity.findViewById<TextView>(R.id.tv_moneyCount).apply {
            visibility = View.GONE
        }
        mainActivity.findViewById<ImageView>(R.id.iv_leftIcon).apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.side_navigation_24dp)
        }
        mainActivity.findViewById<ImageView>(R.id.iv_rightIcon).apply {
            visibility = View.GONE
        }
        val toolbarTitle = requireActivity().findViewById<TextView>(R.id.tv_invoice)
        toolbarTitle.text = "Напоминания"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}