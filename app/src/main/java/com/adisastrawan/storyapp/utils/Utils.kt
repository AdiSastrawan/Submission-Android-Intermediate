package com.adisastrawan.storyapp.utils

fun isValidEmail(email: String): Boolean {
    val emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}\$".toRegex()
    return emailPattern.matches(email)
}