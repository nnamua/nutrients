package com.paulaumann.nutrients.views

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.paulaumann.nutrients.R
import java.lang.Math.*
import java.lang.Math.PI
import kotlin.math.*
import kotlin.math.atan

/**
 * This view shows the user whether or not his nutrient consumption
 * is okay. It should be used in a dashboard (here HomeFragment).
 * It can be extended to use different bars for different nutrients.
 */

open class Indicator : View {

    protected var varelaRound = ResourcesCompat.getFont(context, R.font.varela_round)
    protected var testBorderRect = Rect(0, 0, width, height)
    protected val testBorderPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.RED
    }
    protected val labelPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.fontColorDark)
        textAlign = Paint.Align.LEFT
        typeface = varelaRound
    }
    protected val bgRectPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.white)
        style = Paint.Style.FILL
    }
    protected val minMaxPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.fontColorLight)
        textAlign = Paint.Align.LEFT
        typeface = varelaRound
    }
    protected val cursorPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.fontColorDark)
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        typeface = varelaRound
    }
    protected val barPaint = Paint(ANTI_ALIAS_FLAG).apply { 
        color = ContextCompat.getColor(context, R.color.indicatorBar)
        style = Paint.Style.FILL
    }

    // Label
    protected lateinit var labelCoords: PointF
    var relLabelSize = 0.2f
    var label: String

    // Background Rectangle
    var bgRectCornerRadius = 25f
    var relBgRectHeight = 0.4f
    var relBgRectPadding = 0.05f
    lateinit var bgRect: RectF

    // Minimum/Maximum Labels
    var relMinMaxSize = 0.15f
    protected lateinit var minCoords: PointF
    var min = 0.0
        protected set
    protected lateinit var maxCoords: PointF
    var max = 1.0
        protected set

    // Cursor
    var cursor = 0.77
        set(value) {
            if (value < min || value > max) throw IllegalArgumentException("Cursor value must be greater than the minimum, and bigger than the maximum.")
            field = value
        }
    var relCursorHeight = 0.2f
    var relCursorTriangleCutoff = 0.2f
    protected lateinit var cursorPath: Path
    protected lateinit var cursorLabelCoords: PointF

    // Bar
    var relBarHeight = 0.7f
    var barMin = 0.25
        protected set
    var barMax = 0.75
        protected set
    protected lateinit var barRect: RectF

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.Indicator, 0, 0)
        label = a.getText(R.styleable.Indicator_label).toString()
        a.recycle()
    }

    fun setBounds(min: Double, max: Double){
        if (min > max) throw IllegalArgumentException("Minimum must be smaller than maximum.")
        this.min = min
        this.max = max
    }

    fun setBar(min: Double, max: Double){
        if (min < this.min || min > this.max || max < this.min || max > this.max)
            throw IllegalArgumentException("Bar is outside indicator bounds.")
        if (min > max) throw IllegalArgumentException("Minimum must be smaller than maximum.")
        barMin = min
        barMax = max
    }

    protected fun drawLabel(canvas: Canvas){
        canvas.drawText(label, labelCoords.x, labelCoords.y, labelPaint)
    }

    protected fun drawBackgroundRect(canvas: Canvas){
        canvas.drawRoundRect(bgRect, bgRectCornerRadius, bgRectCornerRadius, bgRectPaint)
    }

    protected fun drawMinMax(canvas: Canvas){
        // min value
        minMaxPaint.textAlign = Paint.Align.LEFT
        canvas.drawText(min.toString(), minCoords.x, minCoords.y, minMaxPaint)

        // max value
        minMaxPaint.textAlign = Paint.Align.RIGHT
        canvas.drawText(max.toString(), maxCoords.x, maxCoords.y, minMaxPaint)
    }

    protected fun drawCursor(canvas: Canvas){
        canvas.drawPath(cursorPath, cursorPaint)
        canvas.drawText(cursor.toString(), cursorLabelCoords.x, cursorLabelCoords.y, cursorPaint)
    }

    protected fun drawBar(canvas: Canvas){
        canvas.drawRoundRect(barRect, barRect.height() / 2, barRect.height() / 2, barPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val bgRectPadding = relBgRectPadding * (h - (paddingTop + paddingBottom))
        val uh = h - (paddingTop + paddingBottom + bgRectPadding) // Usable height
        val uw = w - (paddingLeft + paddingRight) // Usable width

        // Label
        labelPaint.textSize = uh * relLabelSize
        val labelX = paddingLeft.toFloat()
        val labelY = paddingTop + labelPaint.textSize
        labelCoords = PointF(labelX, labelY)

        // Background Rectangle
        val left   = paddingLeft
        val right  = w - paddingRight
        val top    = paddingTop + labelPaint.textSize + bgRectPadding
        val bottom = top + uh * relBgRectHeight
        bgRect = RectF(left.toFloat(), top, right.toFloat(), bottom)

        // Minimum/Maximum labels
        minMaxPaint.textSize = uh * relMinMaxSize
        val minX = paddingLeft.toFloat()
        val minY = paddingTop + labelPaint.textSize + bgRectPadding + bgRect.height() + bgRectPadding + minMaxPaint.textSize
        minCoords = PointF(minX, minY)
        val maxX = width - paddingRight.toFloat()
        val maxY = minY
        maxCoords = PointF(maxX, maxY)

        // Cursor
        cursorPaint.textSize = uh * relCursorHeight
        val cursorHeight = uh * relCursorHeight
        val cursorWidth  = cursorHeight * 0.8f
        val cursorTopX   = (((cursor - min) / (max - min)) * bgRect.width() + paddingLeft).toFloat()
        val cursorTopY   = bgRect.bottom - cursorHeight
        val cursorRight  = PointF(cursorTopX + cursorWidth / 2f, cursorTopY + cursorHeight)
        val cursorLeft   = PointF(cursorTopX - cursorWidth / 2f, cursorTopY + cursorHeight)
        cursorPath = topRoundedTriangle(cursorTopY, cursorTopY + cursorHeight,
                                        cursorTopX - cursorWidth / 2, cursorTopX + cursorWidth / 2,
                                        cursorHeight * relCursorTriangleCutoff)
        cursorLabelCoords = PointF(cursorTopX, bgRect.bottom + bgRectPadding + cursorPaint.textSize)

        // Bar
        val barHeight = bgRect.height() * relBarHeight
        val barLeft   = (((barMin - min) / (max - min)) * bgRect.width() + paddingLeft).toFloat()
        val barRight  = (((barMax - min) / (max - min)) * bgRect.width() + paddingLeft).toFloat()
        val barTop    = bgRect.top + (bgRect.height() - barHeight) / 2
        val barBottom = barTop + barHeight
        barRect = RectF(barLeft, barTop, barRight, barBottom)

        // Testing outside border rect
        testBorderRect = Rect(0, 0, w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null) {
            //canvas.drawRect(testBorderRect, testBorderPaint) // Draws a border (for testing)
            drawLabel(canvas)
            drawBackgroundRect(canvas)
            drawMinMax(canvas)
            drawBar(canvas)
            drawCursor(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                relLabelSize = 0.1f
                relBgRectHeight = 0.5f
                requestLayout()
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                relLabelSize = 0.2f
                relBgRectHeight = 0.4f
                requestLayout()
                performClick()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        invalidate()
        return super.performClick()
    }

    // Returns a path containing a rounded triangle
    // https://www.desmos.com/calculator/el1udmckku
    protected fun topRoundedTriangle(topY: Float, bottomY: Float,
                                     leftX: Float, rightX: Float,
                                     cutoff: Float): Path {
        if (leftX > rightX) throw IllegalArgumentException("Left point must be to the left of the right point.")
        if (bottomY < topY) throw IllegalArgumentException("Bottom point must be below top point.")
        if (cutoff > bottomY - topY) throw IllegalArgumentException("Can't cut off more than triangle height.")

        val w = rightX - leftX
        val h = bottomY - topY
        val centerX = leftX + w / 2
        val angle = atan(w/(2*h))

        val cw = tan(angle) * cutoff * 2 // Width of the triangle at cutoff height
        val topLeft  = PointF(centerX - cw / 2, topY + cutoff)
        val topRight = PointF(centerX + cw / 2, topY + cutoff)
        val btmRight = PointF(rightX, bottomY)
        val btmLeft  = PointF(leftX, bottomY)

        // Values of circle that rounds the top
        val circleOffset = cw.pow(2) / (4*cutoff)
        val circleRadius = sqrt(cw.pow(4)/(16*cutoff.pow(2)) + cw.pow(2)/4)
        val circleRect = RectF(centerX - circleRadius, topY + cutoff + circleOffset - circleRadius,
                               centerX + circleRadius, topY + cutoff + circleOffset + circleRadius )

        // Construct the path
        val path = Path()
        path.moveTo(topLeft.x, topLeft.y)
        val fromAngle  = toDegrees(atan(w/(2.0*h)) + PI)
        val sweepAngle = toDegrees(2.0 * asin(cw / (2 * circleRadius)))
        path.arcTo(circleRect, fromAngle.toFloat(), sweepAngle.toFloat())
        path.lineTo(topRight.x, topRight.y)
        path.lineTo(btmRight.x, btmRight.y)
        path.lineTo(btmLeft.x, btmLeft.y)
        path.close()
        return path
    }
}