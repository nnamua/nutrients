package com.paulaumann.nutrients.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.model.Food
import kotlin.math.abs

class FoodDataListAdapter(context: Context) :
    RecyclerView.Adapter<FoodDataListAdapter.ViewHolder>() {

    var food: Food = Food.empty()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    private val labels: Array<String> = context.resources.getStringArray(R.array.food_data_labels)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_li, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return labels.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.label.text = labels[position]
        if (position == 0){
            holder.value.text = food.reference
            return
        }
        var value = food[3 + position]
        // Prettify numbers
        if (value == null){
            holder.value.text = "k.A."
        } else {
            if (value is Double?){
                if (abs(value % 1) < 0.0000001) value = value.toInt()
                else value = String.format("%.2f", value)
            }
            holder.value.text = value.toString()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label: TextView = view.findViewById(R.id.foodDataLabel)
        val value: TextView = view.findViewById(R.id.foodDataValue)
    }
}