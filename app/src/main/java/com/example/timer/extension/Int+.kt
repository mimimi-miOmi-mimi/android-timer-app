package com.example.timer.extension

fun Int.convertTimeText(): String {
    val hour = this / 3600
    val min = (this - (3600 * hour)) / 60
    val sec = (this - (3600 * hour)) % 60
    return if (this >= 3600) {
        String.format("%d:%02d:%02d", hour, min, sec)
    } else {
        String.format("%d:%02d", min, sec)
    }
}