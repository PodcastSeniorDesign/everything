package me.rooshi.data.repository

import me.rooshi.domain.model.User
import me.rooshi.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {
    override fun isUserLoggedIn() {
        TODO("Not yet implemented")
    }

    override fun logInUser() {
        TODO("Not yet implemented")
    }

    override fun registerUser() {
        TODO("Not yet implemented")
    }

    override fun getUser(): User? {
        TODO("Not yet implemented")
    }

}