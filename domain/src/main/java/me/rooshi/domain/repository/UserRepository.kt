package me.rooshi.domain.repository

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import me.rooshi.domain.model.SocialPost
import me.rooshi.domain.model.SubscriptionListResult
import me.rooshi.domain.model.User

interface UserRepository {

    fun isUserLoggedIn() : Boolean

    fun logInUserEmail(credentials: List<String>) : Observable<String>

    fun logInUserFacebook()

    fun registerUserEmail(credentials: List<String>) : Observable<String>

    fun logOutUser()

    fun getUser() : User

    fun setFavoriteGenre(genres: List<Int>) : Observable<String>

    fun getAllUsers(): Observable<List<User>>

    fun addFriend(id: String): Observable<Pair<String, String>>

    fun unFriend(id: String): Observable<Pair<String, String>>

    fun isFriend(id: String): Observable<Pair<String, Boolean>>

    fun getSocialFeed() : Observable<List<SocialPost>>

    fun createPost(text: String)

    fun createComment(postId: String, text: String)

    fun likePost(postId: String)
    fun unlikePost(postId: String)


}