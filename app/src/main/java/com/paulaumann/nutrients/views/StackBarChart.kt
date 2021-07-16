package com.paulaumann.nutrients.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.withTranslation
import com.paulaumann.nutrients.R

class StackBarChart : View {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val values = mutableMapOf<String, Double>()
    private val rectangles = mutableMapOf<String, RectF>()
    private val labels = mutableMapOf<String, Pair<StaticLayout, PointF>>()

    private var total = 0.0
    private var selected = ""

    private val relRectPadding = 0.02
    private val relBarWidth = 0.5
    private val relBarOffset = 0.02

    private var varelaRound = ResourcesCompat.getFont(context, R.font.varela_round)
    private val rectPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.standardTone)
        style = Paint.Style.FILL
    }
    private val selectedRectPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.highlight)
        style = Paint.Style.FILL
    }
    private val labelPaint = TextPaint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.fontColorDark)
        textAlign = Paint.Align.LEFT
        typeface = varelaRound
        textSize = 50f
    }
    private val selectedLabelPaint = TextPaint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.fontColorDark)
        textAlign = Paint.Align.LEFT
        typeface = Typeface.create(varelaRound, Typeface.BOLD)
        textSize = 60f
    }
    private val extraTextPaint = TextPaint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.fontColorLight)
        textAlign = Paint.Align.LEFT
        typeface = varelaRound
        textSize = 50f
    }

    private var selectedExtraLabel: Pair<StaticLayout, PointF>? = null

    fun add(key: String, value: Double){
        if (key in values) total -= values[key]!!
        values[key] = value
        total += value
        invalidate()
    }

    fun remove(key: String){
        if (key in values){
            total -= values[key]!!
            values.remove(key)
        }
        invalidate()
    }

    fun select(key: String){
        if (key == "" || key in values.keys) {
            val previous = selected
            selected = key

            /* Animate selected:
                (1) Move main label to the top of the rectangle
                (3) ???
             */

            if (key != ""){
                val rect   = rectangles[key]
                val layout = labels[key]?.first
                val pos    = labels[key]?.second

                if (rect != null && layout != null && pos != null) {

                    // (1) This animation moves the main label to the top
                    val from = pos.y
                    val to = rect.top
                    val labelAnim = ValueAnimator.ofFloat(from, to).apply {
                        duration = 200
                        addUpdateListener {
                            labels[key]?.second?.y = animatedValue as Float
                            invalidate()
                        }
                    }

                    // (2) This adds the exact amount below the main label
                    /*
                    val extra = values[key].toString()

                    val width = layout.width
                    val extraFrom = rect.right
                    val extraTo = pos.x
                    val extraY = rect.top + layout.height * 1.3
                    val extraLayout = StaticLayout.Builder
                        .obtain(extra, 0, extra.length, extraTextPaint, width)
                        .build()
                    val extraPos = PointF(-100f, extraY.toFloat())
                    selectedExtraLabel = Pair(extraLayout, extraPos)

                    val extraAnim = ValueAnimator.ofFloat(extraFrom, extraTo).apply {
                        duration = 100
                        addUpdateListener {
                            selectedExtraLabel?.second?.x = animatedValue as Float
                            invalidate()
                        }
                    }
                    */

                    // Play animations
                    val animSet = AnimatorSet()
                    //animSet.playSequentially(labelAnim, extraAnim)
                    animSet.playSequentially(labelAnim)
                    animSet.start()

                }

            }

            onSizeChanged(width, height, width, height)
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val rectPadding = (height - (paddingBottom + paddingTop)) * relRectPadding
        // Height without any padding
        val barH = height - (paddingBottom + paddingTop) - rectPadding * (values.size - 1)
        val barW = width * relBarWidth
        val barOffset = width * relBarOffset

        // Re-calculate rectangles and label positions
        rectangles.clear()
        labels.clear()
        var yOffset = paddingTop.toFloat()
        for (key in values.keys){
            val isSelected = (key == selected)
            val value: Double = values[key] ?: continue
            val rectH = (value / total) * barH
            val left   = if (isSelected) paddingLeft.toFloat() else (paddingLeft + barOffset).toFloat()
            val top    = yOffset
            val right  = if (isSelected) left + barW + barOffset else paddingLeft + barW
            val bottom = yOffset + rectH.toFloat()
            val rect = RectF(left, top, right.toFloat(), bottom)
            rectangles[key] = rect

            val paint = if (key == selected) selectedLabelPaint else labelPaint
            val labelX = right + rectPadding
            val layout = StaticLayout.Builder
                .obtain(key, 0, key.length, paint, (width - paddingRight - labelX).toInt())
                .build()
            val labelY = yOffset + rectH / 2f - layout.height / 2f
            labels[key] = Pair(layout, PointF(labelX.toFloat(), labelY.toFloat()))

            yOffset += (rectH + rectPadding).toFloat()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return;

        // Draw rectangles
        for (key in rectangles.keys){
            val rect = rectangles[key]
            val paint = if (key == selected) selectedRectPaint else rectPaint
            if (rect != null) canvas.drawRect(rect, paint)
        }

        // Draw labels
        for (key in labels.keys){
            val label = labels[key]
            label?.first?.draw(canvas, label.second.x, label.second.y)
        }

        // Draw extra information for selected
        val extraLabel = selectedExtraLabel
        extraLabel?.first?.draw(canvas, extraLabel.second.x, extraLabel.second.y)

        // TODO: (1) Make rectangle expand when selected
        // TODO: (3) Animate everything smoothly
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            var newSelection = false
            for (key in rectangles.keys){
                val rect = rectangles[key]
                if (rect != null && rect.contains(event.x, event.y)){
                    select(key)
                    newSelection = true
                    break
                }
            }
            if (!newSelection) select("")
        }

        return super.onTouchEvent(event)
    }
}

private fun StaticLayout.draw(canvas: Canvas, x: Float, y: Float) {
    canvas.withTranslation(x, y){
        draw(this)
    }
}
