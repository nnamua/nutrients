package com.paulaumann.nutrients.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.data.Food
import com.paulaumann.nutrients.views.StackBarChart

class StackBarFragment(private val pos: Int, private val foodList: List<Food>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.history_stackbar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val stackBarChart = view.findViewById<StackBarChart>(R.id.historyStackBarChart)

        stackBarChart.add("A", 10.0)
        stackBarChart.add("B", 20.0)
    }
}