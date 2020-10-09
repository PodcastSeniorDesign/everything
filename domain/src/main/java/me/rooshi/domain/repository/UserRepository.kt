package me.rooshi.domain.repository

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import me.rooshi.domain.model.User

interface UserRepository {

    fun isUserLoggedIn() : Boolean

    fun logInUserEmail(credentials: List<String>) : Observable<String>

    fun registerUser()

    fun logOutUser()

    fun getUser() : User?
}