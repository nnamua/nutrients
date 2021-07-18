package com.paulaumann.nutrients.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paulaumann.nutrients.BaseFragment
import com.paulaumann.nutrients.databinding.FragmentTodoBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * This Fragment displays a message that the functionality has not been implemented yet.
 */

@AndroidEntryPoint
class TodoFragment : BaseFragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set listener to back button
        binding.todoBack.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}