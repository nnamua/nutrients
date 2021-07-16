package com.paulaumann.nutrients.util

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.paulaumann.nutrients.R
import kotlinx.android.synthetic.main.under_construction.*

class TodoActivity : AppCompatActivity(R.layout.fragment_todo) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set status bar to white with black icons
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.statusBarColor = ContextCompat.getColor(this, R.color.lightGrey)

        // Bind back button
        todoBack.setOnClickListener { finish() }
    }

}