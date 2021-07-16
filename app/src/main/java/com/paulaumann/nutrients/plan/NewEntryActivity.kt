package com.paulaumann.nutrients.plan
/*
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.adapters.FoodListAdapter
import com.paulaumann.nutrients.data.AppDatabase
import com.paulaumann.nutrients.model.Food
import kotlinx.android.synthetic.main.activity_new_entry.*

class NewEntryActivity : AppCompatActivity(R.layout.fragment_plan_newentry) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set status bar to white with black icons
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.statusBarColor = ContextCompat.getColor(this, R.color.lightGrey)

        val dao = AppDatabase.getInstance(this).dao()

        val entries = mutableListOf<Food>()
        val adapter = FoodListAdapter(entries, ::listItemClicked)
        newEntryList.adapter = adapter
        newEntryList.layoutManager = LinearLayoutManager(this)

        newEntryName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == "") return
                entries.clear()
                entries.addAll(dao.getFoodByPartialName(s.toString(), 15))
                adapter.notifyDataSetChanged()
            }

        })

        // Set onclick listener for back button
        newEntryBackButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

    }

    // Called when an item of the entries list is pressed
    fun listItemClicked(food: Food){
        UnaryBottomSheet(food, ::bottomConfirm).apply {
            show(supportFragmentManager, tag)
        }
    }

    // Called by bottom sheet when its confirm button is pressed
    private fun bottomConfirm(foodAndAmount: Pair<Food, Double>){
        val result = Intent()
        result.putExtra("foodID", foodAndAmount.first.id)
        result.putExtra("foodAmount", foodAndAmount.second)
        setResult(Activity.RESULT_OK, result)
        finish()
    }
}


 */