package com.paulaumann.nutrients.dbviewer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulaumann.nutrients.BaseFragment
import com.paulaumann.nutrients.adapters.FoodListAdapter
import com.paulaumann.nutrients.databinding.FragmentDbviewerBinding
import com.paulaumann.nutrients.data.Food
import com.paulaumann.nutrients.viewmodel.DBViewerViewModel

class DBViewerFragment : BaseFragment() {

    private var _binding: FragmentDbviewerBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: DBViewerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDbviewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize list
        val adapter = FoodListAdapter(::listItemClicked)
        binding.dbFoodList.adapter = adapter
        binding.dbFoodList.layoutManager = LinearLayoutManager(mainActivity)

        // Automatically update the list when the data changes
        sharedViewModel.entries.observe(viewLifecycleOwner) {
            adapter.entries = it
        }

        // Set listener to search edittext
        binding.dbSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || s == "") return
                sharedViewModel.findFood(s.toString())
            }
        })

        // Set listeners to navigation buttons
        binding.dbBack.setOnClickListener {
            // TODO: Navigate back
        }
        binding.dbAdd.setOnClickListener {
            // TODO: Navigate to DBAddFragment
        }
    }

    private fun listItemClicked(food: Food){
        // TODO: Navigate to FoodDetailFragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}