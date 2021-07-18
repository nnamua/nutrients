package com.paulaumann.nutrients.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paulaumann.nutrients.data.Consumed
import com.paulaumann.nutrients.data.ConsumedFood
import com.paulaumann.nutrients.history.StackBarFragment
import com.paulaumann.nutrients.data.Food
import com.paulaumann.nutrients.history.PieChartFragment
import java.util.*

class HistoryFragmentAdapter(private val itemcount: Int,
                             fm: FragmentManager,
                             lifecycle: Lifecycle,
                             private val entries: LiveData<List<ConsumedFood>>
) : FragmentStateAdapter(fm, lifecycle) {

    private val colorMapping = mutableMapOf<Int, Int>()

    override fun getItemCount(): Int = itemcount

    override fun createFragment(position: Int): Fragment {
        return PieChartFragment(position, entries, colorMapping)
    }
}