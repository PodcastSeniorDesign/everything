package me.rooshi.domain.repository

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import me.rooshi.domain.model.User

interface UserRepository {

    fun isUserLoggedIn() : Boolean

    fun logInUserEmail(credentials: List<String>) : Observable<String>

    fun logInUserFacebook()

    fun registerUserEmail(credentials: List<String>) : Observable<String>

    fun logOutUser()

    fun getUser() : User?

    fun setFavoriteGenre(genres: List<Int>) : Observable<String>

    fun getAllUsers(): Observable<List<User>>
}