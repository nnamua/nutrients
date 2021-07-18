package com.paulaumann.nutrients.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.paulaumann.nutrients.R

/**
 * Deprecated View for the HomeFragment.
 */

class Panel(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        inflate(context, R.layout.panel, this)

        val imgView: ImageView = findViewById(R.id.img)
        val nameView: TextView = findViewById(R.id.name)

        val a = context.obtainStyledAttributes(attrs, R.styleable.Panel, 0, 0)
        imgView.setImageDrawable(a.getDrawable(R.styleable.Panel_src))
        nameView.text = a.getText(R.styleable.Panel_name)
        a.recycle()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val animatorSet = AnimatorSet()
                val scaleXAnim = ObjectAnimator.ofFloat(this, View.SCALE_X, scaleX, 0.95F)
                val scaleYAnim = ObjectAnimator.ofFloat(this, View.SCALE_Y, scaleY, 0.95F)
                animatorSet.playTogether(scaleXAnim, scaleYAnim)
                animatorSet.duration = 100;
                animatorSet.start()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val animatorSet = AnimatorSet()
                val scaleXAnim = ObjectAnimator.ofFloat(this, View.SCALE_X, scaleX, 1F)
                val scaleYAnim = ObjectAnimator.ofFloat(this, View.SCALE_Y, scaleY, 1F)
                animatorSet.playTogether(scaleXAnim, scaleYAnim)
                animatorSet.duration = 100;
                animatorSet.start()
            }
        }
        return super.onTouchEvent(event)
    }
}
