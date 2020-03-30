package com.deniz.easify.extension

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @User: deniz.demirci
 * @Date: 2019-11-28
 */

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Creates a fade animation for the calling view
 * @param opacity : final opacity of the view, zero by default
 * @param duration : how long time it takes to complete the animation, 500 ms for default
 */
fun View.animateFading(opacity: Float = 0f, duration: Long = 500) {
    val animator = ObjectAnimator.ofFloat(this, View.ALPHA, opacity)
    animator.duration = duration
    animator.start()
}

/**
 * Creates a scaling animation for the calling view
 */
fun View.animateScaling() {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f)
    val animator = ObjectAnimator.ofPropertyValuesHolder(
        this, scaleX, scaleY)
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE
    animator.start()
}
