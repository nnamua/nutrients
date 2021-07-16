package com.paulaumann.nutrients.nutrients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulaumann.nutrients.BaseFragment
import com.paulaumann.nutrients.adapters.FoodDataListAdapter
import com.paulaumann.nutrients.databinding.FragmentNutrientsBinding
import com.paulaumann.nutrients.analytics.Analytics
import com.paulaumann.nutrients.util.WeekPicker
import com.paulaumann.nutrients.viewmodel.NutrientsViewModel

class NutrientsFragment : BaseFragment() {

    private var _binding: FragmentNutrientsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NutrientsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNutrientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize food data list
        val adapter = FoodDataListAdapter(mainActivity)
        binding.sumFoodData.adapter = adapter
        binding.sumFoodData.layoutManager = LinearLayoutManager(mainActivity)

        // Automatically update food data list as data changes
        viewModel.entries.observe(viewLifecycleOwner) { entries ->
            val sum = Analytics.sum(entries)
            adapter.food = sum
        }

        // Set listener to week/year selection button
        binding.nutrientsPickWeek.setOnClickListener {
            WeekPicker(viewModel::weekSelected).apply {
                show(mainActivity.supportFragmentManager, tag)
            }
        }

        // Set listener to navigation buttons
        binding.nutrientsBack.setOnClickListener {
            // TODO: Navigate back
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}