package com.paulaumann.nutrients.history
/*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.adapters.HistoryFragmentAdapter
import com.paulaumann.nutrients.util.WeekPicker
import kotlinx.android.synthetic.main.activity_db_add.*
import kotlinx.android.synthetic.main.activity_history.*
import java.util.*

class HistoryActivity : AppCompatActivity(R.layout.history) {

    private lateinit var fragmentAdapter: HistoryFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val labels: Array<String> = resources.getStringArray(R.array.food_data_labels)

        fragmentAdapter = HistoryFragmentAdapter(labels.size - 1, supportFragmentManager, lifecycle)
        historyViewPager.adapter = fragmentAdapter
        TabLayoutMediator(historyTabLayout, historyViewPager) { tab, position ->
            tab.text = labels[position + 1]
        }.attach()

        // Bind buttons
        historyBack.setOnClickListener { finish() }
        historyPickWeek.setOnClickListener {
            WeekPicker(::weekSelected).apply {
                show(supportFragmentManager, tag)
            }
        }
    }

    private fun weekSelected(week: Int, year: Int){
        fragmentAdapter.update(week, year)
    }
}

 */