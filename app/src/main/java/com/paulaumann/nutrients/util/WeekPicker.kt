package com.paulaumann.nutrients.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paulaumann.nutrients.R
import java.lang.Integer.min
import java.lang.Math.max
import java.text.DateFormat
import java.util.*

/**
 * This BottomSheet allows the user to pick a week and a year.
 * @param callback This function is called with (week, year), when the user confirms the selection
 * @param startWeek Starting week
 * @param startYear Starting year
 */
class WeekPicker(private val callback: (week: Int, year: Int) -> Unit,
                 private var startWeek: Int, private var startYear: Int)
    : BottomSheetDialogFragment() {

    constructor(callback: (week: Int, year: Int) -> Unit) : this(callback, -1, -1)

    private lateinit var weekNumberPicker: NumberPicker
    private lateinit var yearNumberPicker: NumberPicker
    private lateinit var weekTitle: TextView
    private lateinit var weekPreview : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weekpicker_bottom_sheet, container, false)
        // TODO: Switch to ViewBinding
        weekNumberPicker = view.findViewById(R.id.weekNumberPicker)
        yearNumberPicker = view.findViewById(R.id.yearNumberPicker)
        weekTitle        = view.findViewById(R.id.weekTitle)
        weekPreview      = view.findViewById(R.id.weekPreview)
        val weekConfirm  = view.findViewById<Button>(R.id.weekConfirm)

        if (startWeek == -1) startWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)
        if (startYear == -1) startYear = Calendar.getInstance().get(Calendar.YEAR)

        if (startWeek > 52 || startWeek < 0) throw IllegalArgumentException("Starting week must be between 0 and 52.")
        if (startYear < 2000) throw IllegalArgumentException("Starting year must be greater than 1900.")

        // Initialize week picker
        weekNumberPicker.minValue = 0
        weekNumberPicker.maxValue = 52
        weekNumberPicker.value = startWeek

        // Initialize year picker
        yearNumberPicker.minValue = min(startYear, 2020)
        yearNumberPicker.maxValue = max(Calendar.getInstance().get(Calendar.YEAR), startYear)
        yearNumberPicker.value = startYear

        // Bind onChangeListeners
        weekNumberPicker.setOnValueChangedListener { _, _, _ -> updateLabels() }
        yearNumberPicker.setOnValueChangedListener { _, _, _ -> updateLabels() }

        // Bind callback to confirm button
        weekConfirm.setOnClickListener {
            callback(weekNumberPicker.value, yearNumberPicker.value)
            dismiss()
        }

        updateLabels()
        return view
    }

    // Displays the currently selected week, year and the start and end day
    private fun updateLabels(){
        weekTitle.text = "Kalenderwoche ${weekNumberPicker.value}, ${yearNumberPicker.value}"
        val calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.WEEK_OF_YEAR, weekNumberPicker.value)
            set(Calendar.YEAR, yearNumberPicker.value)
            firstDayOfWeek = Calendar.MONDAY
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }
        val weekStart = calendar.time
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val weekEnd   = calendar.time

        val previewString = "( ${DateFormat.getDateInstance().format(weekStart)}" +
                           " - ${DateFormat.getDateInstance().format(weekEnd)} )"
        weekPreview.text = previewString
    }
}