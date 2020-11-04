package me.rooshi.podcastapp.common.util.extensions

import android.view.MotionEvent
import android.view.View

fun View.setVisible(visible: Boolean, invisible: Int = View.GONE) {
    visibility = if (visible) View.VISIBLE else invisible
}

fun View.forwardTouches(parent: View) {
    var isLongClick = false

    setOnLongClickListener {
        isLongClick = true
        true
    }

    setOnTouchListener { v, event ->
        parent.onTouchEvent(event)

        when {
            event.action == MotionEvent.ACTION_UP && isLongClick -> {
                isLongClick = true
                true
            }

            event.action == MotionEvent.ACTION_DOWN -> {
                isLongClick = false
                v.onTouchEvent(event)
            }

            else -> v.onTouchEvent(event)
        }
    }
}
