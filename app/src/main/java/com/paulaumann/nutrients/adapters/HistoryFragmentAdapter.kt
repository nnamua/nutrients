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

/**
 * This class is the adapter for the ViewPager2
 * containing the analysis for each data field of a
 * Food object.
 * @param itemcount Number of pages
 * @param entries LiveData of a List of ConsumedFodd
 * @see Food
 * @see LiveData
 */
class HistoryFragmentAdapter(private val itemcount: Int,
                             fm: FragmentManager,
                             lifecycle: Lifecycle,
                             private val entries: LiveData<List<ConsumedFood>>
) : FragmentStateAdapter(fm, lifecycle) {

    /*
        Maps a Food ID to a color.
        Used for matching colors in PieChart and Description
     */
    private val colorMapping = mutableMapOf<Int, Int>()

    override fun getItemCount(): Int = itemcount

    override fun createFragment(position: Int): Fragment {
        return PieChartFragment(position, entries, colorMapping)
    }
}