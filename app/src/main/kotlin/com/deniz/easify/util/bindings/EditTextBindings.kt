package com.deniz.easify.util.bindings

import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import com.deniz.easify.R
import com.deniz.easify.extension.afterTextChanged
import com.deniz.easify.ui.profile.followings.follow.FollowViewModel
import com.deniz.easify.ui.search.SearchViewModel

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

@BindingAdapter("app:viewModel")
fun setSearchEditTextFeatures(editText: AppCompatEditText, viewModel: SearchViewModel) {

    editText.setOnFocusChangeListener { view , focused ->
        editText.hint = if (!focused) view.context.getString(R.string.search) else ""
    }

    editText.afterTextChanged { input ->
        viewModel.showClearIcon(input.isNotEmpty())
        editText.hint = if (input.isEmpty() && !editText.isFocused) editText.context.getString(R.string.search) else ""
        viewModel.fetchSongs(input)
    }
}

@BindingAdapter("app:viewModel")
fun setFollowEditTextFeatures(editText: AppCompatEditText, viewModel: FollowViewModel) {

    editText.setOnFocusChangeListener { view , focused ->
        editText.hint = if (!focused) view.context.getString(R.string.search) else ""
    }

    editText.afterTextChanged { input ->
        viewModel.showClearIcon(input.isNotEmpty())
        editText.hint = if (input.isEmpty() && !editText.isFocused) editText.context.getString(R.string.search) else ""
        viewModel.fetchArtists(input)
    }
}