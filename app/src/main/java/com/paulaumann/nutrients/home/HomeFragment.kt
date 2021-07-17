package com.paulaumann.nutrients.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.paulaumann.nutrients.BaseFragment
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainThisWeek.setOnClickListener {
            // Launch PlanFragment
            navController.navigate(R.id.action_homeFragment_to_planFragment)
        }
        binding.mainDBViewer.setOnClickListener {
            // Launch DBViewerFragment
            navController.navigate(R.id.action_homeFragment_to_DBViewerFragment)
        }
        binding.mainProfile.setOnClickListener {
            // Launch ProfileFragment
            navController.navigate(R.id.action_homeFragment_to_todoFragment)
        }
        binding.mainNutrients.setOnClickListener {
            // Launch NutrientsFragment
            navController.navigate(R.id.action_homeFragment_to_nutrientsFragment)
        }
        binding.mainHistory.setOnClickListener {
            // Launch HistoryFragment
            navController.navigate(R.id.action_homeFragment_to_historyFragment)
        }

        // Set sample data to indicators
        // TODO: Remove sample data or move to viewmodel
        binding.vitaminIndicator.setBar(0.33, 0.54)
        binding.vitaminIndicator.cursor = 0.4

        binding.natriumIndicator.setBar(0.7, 0.8)
        binding.natriumIndicator.cursor = 0.65
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}