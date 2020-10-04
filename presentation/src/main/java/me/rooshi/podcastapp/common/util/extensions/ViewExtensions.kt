package me.rooshi.podcastapp.common.util.extensions

import android.view.View

fun View.setVisible(visible: Boolean, invisible: Int = View.GONE) {
    visibility = if (visible) View.VISIBLE else invisible
}