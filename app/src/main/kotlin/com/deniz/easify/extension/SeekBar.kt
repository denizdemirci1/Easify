package com.deniz.easify.extension

import android.widget.SeekBar

/**
 * @User: deniz.demirci
 * @Date: 2020-01-02
 */

fun SeekBar.onProgressChanged(onProgressChanged: (Float) -> Unit) {
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
            onProgressChanged.invoke(seekBar.progress.toFloat() / 100)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}

        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    })
}
