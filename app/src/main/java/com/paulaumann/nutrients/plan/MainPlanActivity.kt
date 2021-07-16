package com.paulaumann.nutrients.plan
/*
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.adapters.WeekListAdapter
import com.paulaumann.nutrients.data.AppDatabase
import com.paulaumann.nutrients.data.Consumed
import com.paulaumann.nutrients.data.ConsumedFood
import com.paulaumann.nutrients.data.FoodDao
import com.paulaumann.nutrients.db.*
import com.paulaumann.nutrients.util.WeekPicker
import kotlinx.android.synthetic.main.activity_main_plan.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainPlanActivity : AppCompatActivity() {

    private val entries = HashMap<Int, MutableList<ConsumedFood>>()
    private lateinit var dao: FoodDao;
    private val listAdapter: WeekListAdapter
    private var week: Int
    private var year: Int

    init {
        // Start at current week
        val calendar = Calendar.getInstance()
        week = calendar.get(Calendar.WEEK_OF_YEAR)
        year = calendar.get(Calendar.YEAR)
        // Initialize adapter and entries map
        for (i in 0..6) entries[i] = ArrayList()
        listAdapter = WeekListAdapter(this, entries)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_plan)
        // Set status bar to white with black icons
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.statusBarColor = ContextCompat.getColor(this, R.color.lightGrey)

        // Set header title
        planCalWeek.text = "Kalenderwoche " + week.toString()

        // Fetch entries from database
        dao = AppDatabase.getInstance(this).dao()
        updateList()

        // Set adapter
        planExpListView.setAdapter(listAdapter)

        // Set onclick listener for new entry button
        planNewEntry.setOnClickListener{
            val intent = Intent(this, NewEntryActivity::class.java)
            startActivityForResult(intent, 1)
        }

        // Set onclick listener for buttons
        planBack.setOnClickListener{ finish() }
        planSelectWeek.setOnClickListener {
            WeekPicker(::weekSelected, week, year).apply {
                show(supportFragmentManager, tag)
            }
        }
    }

    /*
        Updates the weekly list with the following steps:
            (1) clear the group food lists
            (2) fetch the list of items from the database
            (3) add all these items to the group food lists
            (4) call notifyDataSetChanged() on the adapter
     */
    private fun updateList(){
        val calendar = GregorianCalendar(Locale.GERMANY).apply {
            set(Calendar.YEAR, year)
            set(Calendar.WEEK_OF_YEAR, week)
        }
        for (day in 1..7){
            calendar.apply { set(Calendar.DAY_OF_WEEK, day); set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0) }
            val after = calendar.time
            calendar.apply { set(Calendar.HOUR_OF_DAY, 23); set(Calendar.MINUTE, 59)}
            val before = calendar.time
            val entries = dao.getConsumedFood(after, before)
            val index = WeekListAdapter.dayToIndex(day)
            listAdapter.getGroup(index)?.clear()
            listAdapter.getGroup(index)?.addAll(entries)
        }
        listAdapter.notifyDataSetChanged()
    }

    /*
        Called when a list item was clicked.
     */
    fun listItemClicked(entry: ConsumedFood, index: Int){
        BinaryBottomSheet(entry, {e -> dao.updateConsumed(e); listAdapter.notifyDataSetChanged()},
                                {e -> dao.deleteConsumed(e); updateList()}
            ).apply {
            show(supportFragmentManager, tag)
        }
    }

    /*
        Called when a week was selected
    */
    private fun weekSelected(week: Int, year: Int){
        this.week = week
        this.year = year
        updateList()
        planCalWeek.text = "Kalenderwoche " + week.toString()
    }

    /*
        Retrieves the date for the given index of the expandable list
     */
    private fun getDate(index: Int): Date{
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        val day = WeekListAdapter.indexToDay(index)
        calendar.apply { set(Calendar.YEAR, year); set(Calendar.WEEK_OF_YEAR, week); set(Calendar.DAY_OF_WEEK, day)}
        return calendar.time
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Make sure the request code matches, the activity returned OK and the intent isn't null
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            val foodId = data.getIntExtra("foodID", -1)
            val foodAmount = data.getDoubleExtra("foodAmount", 0.0)
            // Handle missing foodID extra
            if (foodId == -1) {
                Log.d("PlanActivity", "Could not get food id from new entry activity.")
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            // Handle missing foodAmount extra
            } else if (foodAmount == 0.0) {
                Log.d("PlanActivity", "Could not get food amount from new entry activity. Using default value.")
            } else {
                val food = dao.getFoodById(foodId)
                // Handle database error
                if (food == null){
                    Log.d("PlanActivity", "Could not get food entry from id.")
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                } else {
                    // Calculate date for chosen day
                    val date = getDate(listAdapter.selectedIndex)

                    // Create Consumed object and store it in the database
                    val consumed = Consumed(
                        foodId,
                        date,
                        foodAmount
                    )
                    dao.addConsumed(consumed)

                    // Update the list
                    val consumedFood =
                        ConsumedFood(
                            consumed,
                            food
                        )
                    listAdapter.getGroup(listAdapter.selectedIndex)?.add(consumedFood)
                    listAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}
*/