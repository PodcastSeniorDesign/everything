package me.rooshi.domain.repository

import me.rooshi.domain.model.User

interface UserRepository {

    fun isUserLoggedIn()

    fun logInUser()

    fun registerUser()

    fun getUser() : User?
}