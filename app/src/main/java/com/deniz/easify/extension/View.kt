package com.deniz.easify.extension

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
