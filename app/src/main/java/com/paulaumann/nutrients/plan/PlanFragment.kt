package com.paulaumann.nutrients.plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.paulaumann.nutrients.BaseFragment
import com.paulaumann.nutrients.MainActivity
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.adapters.WeekListAdapter
import com.paulaumann.nutrients.databinding.FragmentPlanBinding
import com.paulaumann.nutrients.util.WeekPicker
import com.paulaumann.nutrients.viewmodel.PlanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanFragment : BaseFragment() {

    private var _binding: FragmentPlanBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: PlanViewModel by activityViewModels()

    private lateinit var listAdapter: WeekListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set calendar week textview
        binding.planCalWeek.text = "Kalenderwoche ${sharedViewModel.week}"

        // Initialize listview
        val selectedIndex = WeekListAdapter.dayToIndex(sharedViewModel.selectedDay)
        listAdapter = WeekListAdapter(activity as MainActivity, selectedIndex)
        binding.planExpListView.setAdapter(listAdapter)
        binding.planExpListView.setOnChildClickListener { _, _, gpos, cpos, _ ->
            val child = listAdapter.getChild(gpos, cpos)
            if (child != null) {
                BinaryBottomSheet(child, {e -> sharedViewModel.updateConsumed(e)},
                                         {e -> sharedViewModel.deleteConsumed(e)}
                ).apply {
                    show(mainActivity.supportFragmentManager, tag)
                }
            }
            return@setOnChildClickListener true
        }
        binding.planExpListView.setOnGroupClickListener { _, _, gpos, _ ->
            val day = WeekListAdapter.indexToDay(gpos)
            sharedViewModel.selectedDay = day
            listAdapter.selectedIndex = gpos
            return@setOnGroupClickListener false
        }


        // Automatically update list when the data updates
        for (i in 0..6) {
            sharedViewModel.consumedFood[i].observe(viewLifecycleOwner) { consumedFood ->
                listAdapter.entries[i] = consumedFood
                listAdapter.notifyDataSetInvalidated()
                listAdapter.notifyDataSetChanged()
            }
        }

        // Set onclick listener for new entry button
        binding.planNewEntry.setOnClickListener {
            navController.navigate(R.id.action_planFragment_to_planNewEntryFragment)
        }

        // Set onclick listeners for navigation buttons
        binding.planBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.planSelectWeek.setOnClickListener {
            val callback: (Int, Int) -> Unit = { w, y -> sharedViewModel.weekSelected(w,y)
                                                         binding.planCalWeek.text = "Kalenderwoche ${w}"   }
            WeekPicker(callback, sharedViewModel.week, sharedViewModel.year).apply {
                show(mainActivity.supportFragmentManager, tag)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
