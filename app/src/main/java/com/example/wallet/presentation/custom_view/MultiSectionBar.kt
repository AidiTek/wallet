package com.example.wallet.presentation.custom_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import com.example.wallet.R
import com.example.wallet.presentation.data.Section

class MultiSectionBar @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context,attrs,defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var sections: List<Section>  = emptyList()

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        strokeWidth = 8f
    }

    init {
        outlineProvider = ViewOutlineProvider.BACKGROUND
        clipToOutline = true
    }


    fun setSection(newSection:List<Section>){
        sections = newSection
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var startX = 0f
        for ((index, section) in sections.withIndex()) {
            paint.color = section.color
            val sectionWidth = (width * section.percentage)

            // Рисуем секцию
            canvas.drawRect(startX, 0f, startX + sectionWidth, height.toFloat(), paint)

            // Рисуем линию (если это не последняя секция)
            if (index < sections.size - 1) {
                canvas.drawLine(
                    startX + sectionWidth, 0f, // Начало линии
                    startX + sectionWidth, height.toFloat(), // Конец линии
                    linePaint
                )
            }

            // Сдвигаем стартовую позицию с учётом промежутка
            startX += sectionWidth
        }
    }
}
