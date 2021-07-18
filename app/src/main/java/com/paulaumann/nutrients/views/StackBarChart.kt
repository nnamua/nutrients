package com.paulaumann.nutrients.views

import android.animation.AnimatorSet
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
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.withTranslation
import androidx.core.view.ViewCompat
import com.paulaumann.nutrients.R
import kotlin.math.ceil
import kotlin.math.max

class StackBarChart : View {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val values = mutableListOf<Pair<String, Double>>()
    private val layouts = mutableListOf<StaticLayout>()
    private val layoutPlacements = mutableListOf<PointF>()
    private val rectangles = mutableListOf<RectF>()

    private var total = 0.0
    private var selected: Int = -1

    private val paddingCenter = 10
    private val selectedScale = 1.2

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

    private fun _add(pair: Pair<String, Double>){
        values.add(pair)
        total += pair.second
        val layout = StaticLayout.Builder
            .obtain(pair.first, 0, pair.first.length, labelPaint,
                (paddingCenter + width) / 2 - paddingRight)
            .build()
        layouts.add(layout)
    }

    private fun update(){
        if (ViewCompat.isLaidOut(this)) {
            requestLayout()
            invalidate()
        }
    }

    fun add(label: String, value: Double){
        if (value < 0.001) return
        _add(Pair(label, value))
        update()
    }

    fun add(list: List<Pair<String, Double>>){
        for (pair in list){
            if (pair.second < 0.001) continue
            _add(pair)
        }
        update()
    }

    fun clear(){
        values.clear()
        layouts.clear()
        total = 0.0
        update()
    }

    fun select(i: Int){
        selected = i

        /* Animate selected:
            (1) Move main label to the top of the rectangle
            (3) ???
         */

        if (i != -1){
            val rect   = rectangles[i]
            val layout = layouts[i]
            val pos    = layoutPlacements[i]

            // (1) This animation moves the main label to the top
            val from = pos.y
            val to = rect.top
            val labelAnim = ValueAnimator.ofFloat(from, to).apply {
                duration = 200
                addUpdateListener {
                    pos.y = animatedValue as Float
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

        recalculate()
        invalidate()
    }

    /*
    In an optimal case, this view is running with layout_height set to wrap_content.
    Only here it is guaranteed, that every label fits onto the screen.
    To guarantee this, final rectangle heights are calculated in the following way:
    First, the text height and rectangle height is calculated for every entry
    (Here, rectangle height means entry value divided by total value, or share of total height).
    By dividing text height by rectangle height, we can calculate by how much
    the rectangle would need to be scaled to match the text.
    We only store the maximum value of these factors (the smallest rectangle compared to its text)
    Now we can iterate over our entries again, this time multiplying the
    rectangle height with our maximum factor. This way, we guarantee that:
    (1) Rectangles share the same ratios as without scaling
    (2) Every rectangle is as least as big as the corresponding text
    */

    private fun calculateHeight(w: Int): Int{
        val rectPadding = 10f

        // Calculate layouts
        layouts.clear()
        for (pair in values){
            val layout = StaticLayout.Builder
                .obtain(pair.first, 0, pair.first.length, labelPaint,
                    (paddingCenter + w) / 2 - paddingRight)
                .build()
            layouts.add(layout)
        }

        // Calculate maxScale
        var maxScale: Double = 1.0
        val rectShares = mutableListOf<Double>()
        for (i in values.indices){
            val value = values[i].second
            val rectTotalShare = value / total
            rectShares.add(rectTotalShare)
            val scale = layouts[i].height / rectTotalShare
            maxScale = max(maxScale, scale)
        }

        // Calculate rectangles and layout placements
        var newHeight = max(0, values.size - 1) * rectPadding.toDouble()
        for (i in values.indices){
            newHeight += rectShares[i] * maxScale
        }

        return ceil(newHeight).toInt()
    }

    private fun recalculate(){
        val rectPadding = 10f
        // |  <->   RECT  <->   TEXT  <-> |
        //   pLeft      pCenter      pRight
        val rectWidth = (width - paddingLeft - paddingCenter - paddingRight) / (2 * selectedScale)
        val barOffset = rectWidth / 2f * (selectedScale - 1)
        val totalHeight = height - (paddingTop + paddingBottom) - (values.size - 1) * rectPadding

        // Calculate rectangles and layout placements
        rectangles.clear()
        layoutPlacements.clear()
        var yOffset = paddingTop.toFloat()
        for (i in values.indices){
            // Rectangle
            val rectHeight = (values[i].second / total) * totalHeight
            val isSelected = (i == selected)
            val left   = if (isSelected) paddingLeft.toFloat() else (paddingLeft + barOffset).toFloat()
            val top    = yOffset
            val right  = if (isSelected) (width - paddingCenter) / 2 else (width - paddingCenter) / 2 - barOffset
            val bottom = yOffset + rectHeight
            val rect = RectF(left, top, right.toFloat(), bottom.toFloat())
            rectangles.add(rect)

            // Layout placement
            val layout = layouts[i]
            val layoutX = (width + paddingCenter) / 2
            val layoutY = yOffset + rectHeight / 2f - layout.height / 2f
            layoutPlacements.add(PointF(layoutX.toFloat(), layoutY.toFloat()))

            yOffset += rectHeight.toFloat() + rectPadding
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED){
            // Calculate fitting height for wrap_content
            val height = calculateHeight(width)
            setMeasuredDimension(width, height)
        } else {
            val height = MeasureSpec.getSize(heightMeasureSpec)
            setMeasuredDimension(width, height)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        recalculate()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return;

        // Draw rectangles
        for (i in values.indices){
            val rect = rectangles[i]
            val paint = if (i == selected) selectedRectPaint else rectPaint
            canvas.drawRect(rect, paint)
        }

        // Draw labels
        for (i in values.indices){
            val layout = layouts[i]
            val pos = layoutPlacements[i]
            layout.draw(canvas, pos.x, pos.y)
        }

        // Draw extra information for selected
        //val extraLabel = selectedExtraLabel
        //extraLabel?.first?.draw(canvas, extraLabel.second.x, extraLabel.second.y)

        // TODO: (1) Make rectangle expand when selected
        // TODO: (3) Animate everything smoothly
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            for (i in 0 until rectangles.size){
                val rect = rectangles[i]
                if (rect.contains(event.x, event.y)){
                    if (selected == i) select(-1) else select(i)
                    break
                }
            }
        }

        return super.onTouchEvent(event)
    }
}

private fun StaticLayout.draw(canvas: Canvas, x: Float, y: Float) {
    canvas.withTranslation(x, y){
        draw(this)
    }
}
