package com.deniz.easify.util

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import androidx.databinding.BindingAdapter
import com.makeramen.roundedimageview.RoundedImageView


/**
 * @User: deniz.demirci
 * @Date: 2019-11-20
 */

@BindingAdapter("imageUrl")
fun loadImage(view: RoundedImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}

@BindingAdapter("imageUrl")
fun loadTrackImage(view: AppCompatImageView, url: String?) {
    Glide.with(view.context).load(url).into(view)
}