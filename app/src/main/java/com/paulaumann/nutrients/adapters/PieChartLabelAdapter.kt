package com.paulaumann.nutrients.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.paulaumann.nutrients.MainActivity
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.data.ConsumedFood
import com.paulaumann.nutrients.databinding.FragmentPieChartGiBinding
import com.paulaumann.nutrients.databinding.FragmentPieChartLiBinding
import java.text.DateFormat

class PieChartLabelAdapter(
    private val activity: AppCompatActivity,
    private val colorMapping: MutableMap<Int, Int>
) : BaseExpandableListAdapter() {

    var groups = mutableListOf<List<ConsumedFood>>()
    set(value) {
        field = value
        notifyDataSetInvalidated()
    }

    override fun getGroupCount(): Int {
        return groups.size
    }

    override fun getChildrenCount(gpos: Int): Int {
        return groups[gpos].size
    }

    override fun getGroup(gpos: Int): List<ConsumedFood> {
        return groups[gpos]
    }

    override fun getChild(gpos: Int, cpos: Int): ConsumedFood {
        return groups[gpos][cpos]
    }

    override fun getGroupId(gpos: Int): Long {
        return groups[gpos][0].consumed.foodId.toLong()
    }

    override fun getChildId(gpos: Int, cpos: Int): Long {
        return groups[gpos][cpos].consumed.consumedId
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        gpos: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val firstChild = getChild(gpos, 0)
        var view = convertView
        val binding: FragmentPieChartGiBinding
        if (view == null){
            val inflater = LayoutInflater.from(parent?.context)
            binding = FragmentPieChartGiBinding.inflate(inflater, parent, false)
            view = binding.root
        } else {
            binding = FragmentPieChartGiBinding.bind(view)
        }

        val color = colorMapping[firstChild.consumed.foodId] ?: Color.BLACK
        val unwrappedDrawable = ContextCompat.getDrawable(activity, R.drawable.rounded_rect)
        if (unwrappedDrawable != null) {
            val drawable = DrawableCompat.wrap(unwrappedDrawable).mutate()
            DrawableCompat.setTint(drawable, color)
            binding.pieChartGroupColor.background = drawable
        }
        binding.pieChartGroupTitle.text = firstChild.food.name

        return view
    }

    override fun getChildView(
        gpos: Int,
        cpos: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val child = getChild(gpos, cpos)
        var view = convertView
        val binding: FragmentPieChartLiBinding
        if (view == null){
            val inflater = LayoutInflater.from(parent?.context)
            binding = FragmentPieChartLiBinding.inflate(inflater, parent, false)
            view = binding.root
        } else {
            binding = FragmentPieChartLiBinding.bind(view)
        }

        binding.pieChartFoodAmount.text = "${child.consumed.amount} ${child.food.getReferenceUnit()}"
        binding.pieChartFoodDate.text = "Am ${DateFormat.getDateInstance().format(child.consumed.date)}"

        return view
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}