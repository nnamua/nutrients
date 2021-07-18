package com.paulaumann.nutrients.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.paulaumann.nutrients.adapters.PieChartLabelAdapter
import com.paulaumann.nutrients.data.ConsumedFood
import com.paulaumann.nutrients.databinding.FragmentPieChartBinding
import com.paulaumann.nutrients.util.MaterialColorGenerator

/**
 * This Fragment displays a Pie Chart containing the shares
 * of different ConsumedFood entries for a certain data field (nutrient)
 * @param pos Position in the ViewPager, used to calculate index for Food data field
 * @param entries LiveData of a List of ConsumedFood
 * @param colorMapping Map that contains the color mapping for entries
 * @see PieChartLabelAdapter
 */

class PieChartFragment(private val pos: Int,
                       private val entries: LiveData<List<ConsumedFood>>,
                       private val colorMapping: MutableMap<Int, Int>
) : Fragment() {

    private var _binding: FragmentPieChartBinding? = null
    private val binding get() = _binding!!

    private val groupedEntries = mutableMapOf<Int, List<ConsumedFood>>()

    private lateinit var listAdapter: PieChartLabelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPieChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entries.observe(viewLifecycleOwner) {
            updateChart(it)
        }

        // Initialize list
        listAdapter = PieChartLabelAdapter(activity as AppCompatActivity, colorMapping)
        binding.pieChartList.setAdapter(listAdapter)

        // Allow only one group to be expanded at any time
        // Select the pie chart slice for the expanded group
        binding.pieChartList.setOnGroupExpandListener { pos ->
            for (i in 0 until listAdapter.groupCount){
                if (i != pos) binding.pieChartList.collapseGroup(i)
            }
            binding.pieChart.selected = listAdapter.getGroupId(pos).toInt()
        }
        binding.pieChartList.setOnGroupCollapseListener {
            binding.pieChart.selected = -1
        }
    }

    private fun updateChart(weeklyList: List<ConsumedFood>){
        // Update pie chart
        binding.pieChart.clear()
        groupedEntries.clear()
        val byFood = weeklyList.groupBy { cf -> cf.consumed.foodId }
        for (group in byFood.values){
            var sum = 0.0
            for (consumedFood in group){
                val food = consumedFood.food
                val value = food[pos + 4] as Number
                sum += value.toDouble() * (consumedFood.consumed.amount / food.getReferenceAmount())
            }
            // If the value is less than this threshold, ignore it
            if (sum < 0.0001) continue
            val foodId = group[0].consumed.foodId
            groupedEntries[foodId] = group
            val color: Int
            if (foodId in colorMapping.keys){
                color = colorMapping.getValue(foodId)
            } else {
                color = MaterialColorGenerator.palette()
                colorMapping[foodId] = color
            }
            binding.pieChart.add(foodId, sum, color)
        }
        binding.pieChart.update()

        // Update list
        val groups = mutableListOf<List<ConsumedFood>>()
        groups.addAll(groupedEntries.values)
        listAdapter.groups = groups
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}