package com.paulaumann.nutrients.plan

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulaumann.nutrients.BaseFragment
import com.paulaumann.nutrients.adapters.FoodListAdapter
import com.paulaumann.nutrients.databinding.FragmentPlanNewentryBinding
import com.paulaumann.nutrients.data.Food
import com.paulaumann.nutrients.viewmodel.PlanNewEntryViewModel
import com.paulaumann.nutrients.viewmodel.PlanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanNewEntryFragment : BaseFragment() {

    private var _binding: FragmentPlanNewentryBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: PlanViewModel by activityViewModels()
    private val viewModel: PlanNewEntryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanNewentryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize list
        val adapter = FoodListAdapter(::listItemClicked)
        binding.newEntryList.adapter = adapter
        binding.newEntryList.layoutManager = LinearLayoutManager(mainActivity)

        // Automatically update the list when the data changes
        viewModel.food.observe(viewLifecycleOwner) {
            adapter.entries = it
        }

        // Set listener to search edittext
        binding.newEntryName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || s == "") return
                viewModel.findFood(s.toString())
            }
        })

        // Set listener for navigation button
        binding.newEntryBackButton.setOnClickListener {
            navController.popBackStack()
        }
    }

    // Called when an item of the entries list is pressed
    private fun listItemClicked(food: Food){
        val callback: (Food, Double) -> Unit = { f, a -> sharedViewModel.addConsumed(f, a)
                                                         navController.popBackStack() }
        UnaryBottomSheet(food, callback).apply {
            show(mainActivity.supportFragmentManager, tag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}