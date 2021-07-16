package com.paulaumann.nutrients.dbviewer
/*
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.adapters.FoodListAdapter
import com.paulaumann.nutrients.data.AppDatabase
import com.paulaumann.nutrients.model.Food
import com.paulaumann.nutrients.util.FoodActivity
import kotlinx.android.synthetic.main.dbviewer.*

class DBViewerActivity : AppCompatActivity(R.layout.fragment_dbviewer){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set status bar to white with black icons
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.statusBarColor = ContextCompat.getColor(this, R.color.lightGrey)

        val dao = AppDatabase.getInstance(this).dao()

        // List
        val entries = mutableListOf<Food>()
        val adapter = FoodListAdapter(entries, ::listItemClicked)
        dbFoodList.adapter = adapter
        dbFoodList.layoutManager = LinearLayoutManager(this)

        // Search function
        dbSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == "") return
                entries.clear()
                entries.addAll(dao.getFoodByPartialName(s.toString(), 50))
                adapter.notifyDataSetChanged()
            }
        })

        // Back button
        dbBack.setOnClickListener { finish() }

        // Add Button
        dbAdd.setOnClickListener {
            val intent = Intent(this, DBAddEntryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun listItemClicked(food: Food){
        val intent = Intent(this, FoodActivity::class.java)
        intent.putExtra("foodId", food.id)
        startActivity(intent)
    }
}

 */