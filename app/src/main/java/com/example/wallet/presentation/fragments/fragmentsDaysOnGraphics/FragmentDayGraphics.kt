package com.example.wallet.presentation.fragments.fragmentsDaysOnGraphics

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wallet.R
import com.example.wallet.databinding.FragmentDayGraphicsBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class FragmentDayGraphics : Fragment() {

    private var _binding : FragmentDayGraphicsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayGraphicsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBarChart(binding.barChart)

    }

    private fun setUpBarChart(barchart: BarChart) {

        val incomeEntries = listOf(
            BarEntry(1f, 500f),
            BarEntry(2f, 700f)
        )
        val expenseEntries = listOf(
            BarEntry(1f, 300f),
            BarEntry(2f, 400f)
        )

        val incomeDataSet = BarDataSet(incomeEntries, "Доходы").apply {
            setColors(Color.GREEN)
            valueTextColor = Color.WHITE
        }
        val expendDataSet = BarDataSet(expenseEntries, "Расходы").apply {
            setColors(Color.YELLOW)
            valueTextColor = Color.WHITE
        }

        val barData = BarData(incomeDataSet, expendDataSet).apply {
            barWidth = 0.3f
        }

        barchart.data = barData

        barchart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            textColor = Color.WHITE
            valueFormatter = IndexAxisValueFormatter(listOf("07.10", "28.10"))
        }

        barchart.axisLeft.apply {
            textColor = Color.WHITE
            axisMinimum = 0f
        }

        barchart.axisRight.isEnabled = false

        barchart.apply {
            description.isEnabled = false
            legend.textColor = Color.WHITE
            groupBars(0f,0.4f,0.02f)
            setScaleEnabled(false)
            animateY(1000)
        }

        barchart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}