package com.paulaumann.nutrients.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.paulaumann.nutrients.BaseFragment
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.adapters.HistoryFragmentAdapter
import com.paulaumann.nutrients.databinding.FragmentHistoryBinding
import com.paulaumann.nutrients.util.WeekPicker
import com.paulaumann.nutrients.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var fragmentAdapter: HistoryFragmentAdapter

    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val labels: Array<String> = resources.getStringArray(R.array.food_data_labels)

        // Load pageViewer
        fragmentAdapter = HistoryFragmentAdapter(
            labels.size - 1,
            mainActivity.supportFragmentManager,
            lifecycle,
            viewModel.entries )
        binding.historyViewPager.adapter = fragmentAdapter
        TabLayoutMediator(binding.historyTabLayout, binding.historyViewPager) { tab, position ->
            tab.text = labels[position + 1]
        }.attach()

        // Set listener to week/year selection button
        binding.historyPickWeek.setOnClickListener {
            WeekPicker(viewModel::weekSelected).apply {
                show(mainActivity.supportFragmentManager, tag)
            }
        }

        // Set listener to navigation buttons
        binding.historyBack.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}