package com.nadhif.hayazee.android_core.extension

import android.view.View
import android.view.View.*

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.invisible() {
    this.visibility = INVISIBLE
}

fun View.gone() {
    this.visibility = GONE
}
