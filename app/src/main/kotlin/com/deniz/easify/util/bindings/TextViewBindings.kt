package com.deniz.easify.util.bindings

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

@BindingAdapter("slideText")
fun slideText(view: AppCompatTextView, shouldSlide: Boolean) {
    view.isSelected = shouldSlide
}