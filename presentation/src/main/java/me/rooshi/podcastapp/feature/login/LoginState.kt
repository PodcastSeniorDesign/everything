package me.rooshi.podcastapp.feature.login

data class LoginState(
        val emailFilled: Boolean = false,
        val passwordFilled: Boolean = false
)