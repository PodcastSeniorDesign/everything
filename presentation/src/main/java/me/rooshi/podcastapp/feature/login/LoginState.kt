package me.rooshi.podcastapp.feature.login

data class LoginState(
        val emailFilled: Boolean = false,
        val passwordFilled: Boolean = false,
        val loginMessage: String = "",
        val loggedIn: Boolean = false,
        val registering: Boolean = false
)