package com.paulaumann.nutrients.dbviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.paulaumann.nutrients.BaseFragment
import com.paulaumann.nutrients.databinding.FragmentDbviewerAddBinding

/**
 * This Fragment allows the user to add various entries to the database.
 */
class DBAddEntryFragment : BaseFragment() {

    private var _binding: FragmentDbviewerAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDbviewerAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val choices = arrayOf("Eintrag", "Alias")
        val adapter = ArrayAdapter(mainActivity, android.R.layout.simple_spinner_item, choices)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dbAddSpinner.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}