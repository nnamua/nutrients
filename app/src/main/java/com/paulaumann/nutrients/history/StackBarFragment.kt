package com.paulaumann.nutrients.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.data.ConsumedFood
import com.paulaumann.nutrients.data.Food
import com.paulaumann.nutrients.databinding.HistoryStackbarBinding
import com.paulaumann.nutrients.views.StackBarChart

class StackBarFragment(private val pos: Int,
                       private val entries: LiveData<List<ConsumedFood>>
) : Fragment() {

    private var _binding: HistoryStackbarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HistoryStackbarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        entries.observe(viewLifecycleOwner) {
            updateChart(it)
        }
        //binding.historyStackBarChart.add("A", 2.0)
        //binding.historyStackBarChart.add("X", 5.0)
    }

    private fun updateChart(weeklyList: List<ConsumedFood>){
        binding.historyStackBarChart.clear()
        val list = mutableListOf<Pair<String, Double>>()
        for (entry in weeklyList){
            val value: Number = entry.food[pos + 4] as Number
            list.add(Pair(entry.food.name, value.toDouble()))
        }
        binding.historyStackBarChart.add(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}