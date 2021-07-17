package com.paulaumann.nutrients.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulaumann.nutrients.BaseFragment
import com.paulaumann.nutrients.adapters.FoodDataListAdapter
import com.paulaumann.nutrients.databinding.FragmentFoodDetailBinding
import com.paulaumann.nutrients.viewmodel.FoodDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailFragment : BaseFragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FoodDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoodDataListAdapter(mainActivity)
        binding.foodDataList.adapter = adapter
        binding.foodDataList.layoutManager = LinearLayoutManager(mainActivity)

        val safeArgs: FoodDetailFragmentArgs by navArgs()
        viewModel.loadFood(safeArgs.foodId)

        // Automatically change textview content when data changes
        viewModel.food.observe(viewLifecycleOwner) { food ->
            if (food == null) return@observe

            binding.foodName.text = food.name
            binding.foodCategory.text = food.category
            adapter.food = food
        }

        // Set listeners to navigation buttons
        binding.foodBackButton.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}