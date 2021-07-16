package com.paulaumann.nutrients.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.data.Food

class FoodListAdapter(private val listItemClicked: (Food) -> Unit) :
        RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {

    var entries: List<Food> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plan_newentry_li, parent, false)
        val holder = ViewHolder(view)
        view.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val pos = holder.bindingAdapterPosition
                if (pos == RecyclerView.NO_POSITION || pos < 0 || pos >= entries.size) return
                listItemClicked(entries[pos])
            }

        })
        return holder
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.name.text = entry.name
        holder.category.text = entry.category
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name: TextView     = view.findViewById(R.id.entryListItemName)
        val category: TextView = view.findViewById(R.id.entryListItemCategory)
    }
}