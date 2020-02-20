package com.deniz.easify.util.bindings

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.databinding.BindingAdapter

/**
 * @User: deniz.demirci
 * @Date: 2019-12-31
 */

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter("app:progressNonEditable")
fun setNonEditableProgress(seekBar: AppCompatSeekBar, progress: Float) {
    seekBar.setOnTouchListener { _, _ -> return@setOnTouchListener true }
    seekBar.progress = progress.toInt()
}

@BindingAdapter("app:progressEditable")
fun setEditableProgress(seekBar: AppCompatSeekBar, progress: Float) {
    seekBar.progress = progress.toInt()
}
