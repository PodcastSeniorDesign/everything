package me.rooshi.podcastapp.feature.register

data class RegisterState(
        val emailFilled: Boolean = false,
        val passwordFilled: Boolean = false,
        val finished: Boolean = false,
        val loginMessage: String = "",
        val loggedIn: Boolean = false,
        val cancel: Boolean = false
        )