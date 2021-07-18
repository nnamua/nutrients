package com.paulaumann.nutrients.adapters

import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.paulaumann.nutrients.MainActivity
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.data.ConsumedFood
import java.util.*

class WeekListAdapter(private val activity: MainActivity,
                      var selectedIndex: Int
) : BaseExpandableListAdapter() {

    /*
        Important difference between day and index:

              | mo | di | mi | do | fr | sa | so |
        ------+----+----+----+----+----+----+----+
        day   |  2 |  3 |  4 |  5 |  6 |  7 |  8 |
        ------+----+----+----+----+----+----+----+
        index |  0 |  1 |  2 |  3 |  4 |  5 |  6 |

        For conversion use the supplied methods.
        Access via getGroup(groupPosition) uses the index.
     */

    companion object {
        private val dayNames = arrayOf("mo", "di", "mi", "do", "fr", "sa", "so")

        fun dayToIndex(day: Int): Int { return (day + 5) % 7 }
        fun indexToDay(index: Int): Int { return index + 2}

    }

    // List mapping each day to a list of plan entries
    var entries: MutableList<List<ConsumedFood>> = MutableList(7){ listOf() }
        set(value) {
            field = value
            notifyDataSetInvalidated()
        }

    override fun getGroup(groupPosition: Int): List<ConsumedFood> {
        return entries[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        // Inflate the layout
        val day = dayNames[groupPosition]
        var view = convertView
        if (view == null){
            val layoutInflater = LayoutInflater.from(parent?.context)
            view = layoutInflater.inflate(R.layout.plan_gi, parent, false)!!
        }
        val textView = view.findViewById<TextView>(R.id.planListGroupTitle)
        textView.text = day

        // Color the selected day
        if (groupPosition == selectedIndex){
            textView.background = ContextCompat.getDrawable(activity, R.drawable.rounded_rect)
            textView.setTextColor(Color.WHITE)
        } else {
            textView.background = null
            textView.setTextColor(ContextCompat.getColor(activity, R.color.fontColorDark))
        }

        // Animate the expand icon based on whether or not the group is expanded
        // TODO: This should not animate views that have not explicitly been pressed
        val indicator = view.findViewById<ImageView>(R.id.planListGroupIndicator)
        if (isExpanded){
            val anim = ObjectAnimator.ofFloat(indicator, View.ROTATION, indicator.rotation, 180F)
            anim.duration = 100
            anim.start()
        } else {
            val anim = ObjectAnimator.ofFloat(indicator, View.ROTATION, indicator.rotation, 0F)
            anim.duration = 100
            anim.start()
        }
        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return entries[groupPosition].size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): ConsumedFood? {
        return entries[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val entry = getChild(groupPosition, childPosition)
        var view = convertView;
        if (view == null){
            val layoutInflater = LayoutInflater.from(parent?.context)
            view = layoutInflater.inflate(R.layout.plan_li, parent, false)!!
        }
        val name = view.findViewById<TextView>(R.id.planListItemName)
        val amount = view.findViewById<TextView>(R.id.planListItemAmount)
        if (entry != null){
            name?.text = entry.food.name
            amount?.text = entry.consumed.amount.toInt().toString() + entry.food.getReferenceUnit()
        }
        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return entries.size
    }
}