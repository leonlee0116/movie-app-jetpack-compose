package com.leondev.movie_app.util

fun convertMinuteToHour(minute: Int): String {
    val hours = minute / 60
    val minutesRemaining = minute % 60
    return "${hours}h ${minutesRemaining}m"
}