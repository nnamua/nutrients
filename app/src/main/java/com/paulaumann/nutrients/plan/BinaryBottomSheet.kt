package com.paulaumann.nutrients.plan

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.data.Consumed
import com.paulaumann.nutrients.data.ConsumedFood
import java.lang.NumberFormatException
import kotlin.math.log10
import kotlin.math.pow

/**
 * This BottomSheet is used to edit or delete exisiting Consumed entries.
 * @param entry The entry that is to be edited/deleted
 * @param confirmCallback This method is called when the user chooses confirm
 * @param deleteCallback This method is called when the user chooses delete
 */
class BinaryBottomSheet(private val entry: ConsumedFood,
                        private val confirmCallback: (Consumed) -> Unit,
                        private val deleteCallback:  (Consumed) -> Unit
) : BottomSheetDialogFragment() {

    companion object {
        const val FUNC_SWITCH = 75
        const val MAX_AMOUNT = 2000 + 2 // This value must be greater than 1000. The 2 is added to compensate
        // for flooring the value and the actual maximum being MAX_AMOUNT - 1
        private lateinit var amountValues: DoubleArray

    }

    init {
        // Precompute values for each possible progress value
        amountValues = DoubleArray(101)
        for (i in 0..100) amountValues[i] = progressToAmount(i)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.binary_bottom_sheet, container, false)
        val name = view.findViewById<TextView>(R.id.bottomSheetName)
        val amountLabel = view.findViewById<TextView>(R.id.bottomAmountLabel)
        val amountInput = view.findViewById<EditText>(R.id.bottomAmountInput)
        val seekBar = view.findViewById<SeekBar>(R.id.bottomAmountSlider)
        val confirmButton = view.findViewById<Button>(R.id.bottomConfirm)
        val deleteButton = view.findViewById<Button>(R.id.bottomDelete)
        // Set name
        name.text = entry.food.name
        // Set amount label to reference value
        amountLabel.text = entry.food.getReferenceUnit()

        // Create a textwatcher for the edittext
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ( s == null || s.isEmpty()){
                    confirmButton.isEnabled = false
                    return
                }
                val amount: Double
                try {
                    amount = s.toString().toDouble()
                } catch (e: NumberFormatException){
                    confirmButton.isEnabled = false
                    return
                }
                if (amount < 1){
                    confirmButton.isEnabled = false
                    return
                }
                val progress = amountToProgress(amount)
                seekBar.progress = progress
                confirmButton.isEnabled = true
            }

        }

        // Set seekbar default value and change listener
        seekBar.progress = amountToProgress(entry.consumed.amount)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!fromUser) return
                val amount = progressToAmount(progress)
                val amountString = amount.toInt().toString()
                // Remove text watcher when changing the text (hacky solution but it works)
                amountInput.removeTextChangedListener(textWatcher)
                amountInput.setText(amountString)
                amountInput.removeTextChangedListener(textWatcher)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set amount edittext default value and change listener
        amountInput.setText(entry.consumed.amount.toString())
        amountInput.addTextChangedListener(textWatcher)

        // Set callback to activity
        confirmButton.setOnClickListener {
            entry.consumed.amount = amountInput.text.toString().toDouble()
            confirmCallback(entry.consumed)
            dismiss()
        }
        deleteButton.setOnClickListener {
            deleteCallback(entry.consumed)
            dismiss()
        }

        return view
    }

    // Graph: https://www.desmos.com/calculator/lkyseg8dym
    private fun progressToAmount(progress: Int): Double {
        if (progress < FUNC_SWITCH){
            val a = 1000.0.pow(1.0 / FUNC_SWITCH)
            return a.pow(progress.toDouble())
        } else {
            val a = (MAX_AMOUNT - 1000.0).pow(1.0 / (100 - FUNC_SWITCH))
            return a.pow(progress.toDouble() - FUNC_SWITCH) + 999
        }
    }

    private fun amountToProgress(amount: Double): Int {
        if (amount < 1000){
            val a = 1000.0.pow(1.0 / FUNC_SWITCH)
            return (log10(amount) / log10(a)).toInt()
        } else {
            val a = (MAX_AMOUNT - 1000.0).pow(1.0 / (100 - FUNC_SWITCH))
            return (log10(amount - 999) / log10(a) + FUNC_SWITCH).toInt()
        }
    }
}