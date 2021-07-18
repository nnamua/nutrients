package com.paulaumann.nutrients.views

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.text.StaticLayout
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.withTranslation
import androidx.core.view.ViewCompat
import java.lang.Integer.min
import kotlin.properties.Delegates

/**
 * This custom View displays a pie chart. Entries
 * can be dynamically added or cleared. When adding entries,
 * a call to update() is required afterwards. This is not done automatically,
 * because it is quite processing heavy, and calling it for every item (for example in a list)
 * would be unnecessary.
 */

class PieChart : View {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private data class PieChartEntry (
        val id: Int,
        val value: Double
    ){
        lateinit var paint: Paint
        var startAngle by Delegates.notNull<Float>()
        var sweepAngle by Delegates.notNull<Float>()
    }

    private val entries = mutableListOf<PieChartEntry>()
    private fun sumEntryValues() = entries.fold(0.0){ sum, entry -> sum + entry.value}

    // Layout variables
    private val selectedMultiplier = 1.1f
    var selected = -1
    set(value) {
        field = value
        invalidate()
    }
    private lateinit var circleRect: RectF
    private lateinit var selectedRect: RectF

    /**
     * Add a value to the pie chart.
     * A call to update() is required after adding elements!
     * @param id Used for selection
     * @param value Displayed value
     * @param color Color of the slice
     * @see PieChart#update()
     */
    fun add(id: Int, value: Double, color: Int){
        val entry = PieChartEntry(id, value)
        entry.paint = Paint(ANTI_ALIAS_FLAG).apply {
            this.color = color
            style = Paint.Style.FILL_AND_STROKE
        }
        entries.add(entry)
    }

    /**
     * Clears all values of the pie chart.
     * No call to update required.
     */
    fun clear(){
        entries.clear()
        update()
    }

    /**
     * Updates the view (must be called explicitly after new elements were added.
     * Has no effect when the view has not been laid out yet.
     */
    fun update(){
        if (ViewCompat.isLaidOut(this)) {
            calculate()
            invalidate()
        }
    }

    private fun calculateHeight(w: Int): Int {
        // Height consists of the circle and padding
        // Circle height

        return w + paddingTop + paddingBottom
    }

    private fun calculate(){
        // Basic values
        val circleDiameter = width - (paddingLeft + paddingTop)
        val circleRadius = circleDiameter / 2f
        val center = PointF(paddingLeft + circleRadius, paddingTop + circleRadius)
        selectedRect = RectF(
            center.x - circleRadius,
            center.y - circleRadius,
            center.x + circleRadius,
            center.y + circleRadius
        )
        circleRect = RectF(
            center.x - circleRadius / selectedMultiplier,
            center.y - circleRadius / selectedMultiplier,
            center.x + circleRadius / selectedMultiplier,
            center.y + circleRadius / selectedMultiplier
        )


        val total = sumEntryValues()
        // Calculate values for each entry
        var angle = 0.0
        for (entry in entries){
            // Calculate circle values
            val share = entry.value / total
            entry.startAngle = angle.toFloat()
            angle += share * 360
            entry.sweepAngle = angle.toFloat() - entry.startAngle
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode  = MeasureSpec.getMode(heightMeasureSpec)

        val height = when (heightMode) {
            MeasureSpec.UNSPECIFIED -> calculateHeight(w)
            MeasureSpec.AT_MOST -> min(calculateHeight(w), h)
            else -> h
        }
        setMeasuredDimension(w, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        calculate()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        for (entry in entries){
            // Draw arcs
            val rect = if (entry.id == selected) selectedRect else circleRect
            canvas.drawArc(
                rect,
                entry.startAngle,
                entry.sweepAngle,
                true,
                entry.paint
            )
        }
    }
}

private fun StaticLayout.draw(canvas: Canvas, x: Float, y: Float) {
    canvas.withTranslation(x, y){
        draw(this)
    }
}