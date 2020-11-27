package me.rooshi.data.repository

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.functions.FirebaseFunctions
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import me.rooshi.domain.model.*
import me.rooshi.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
        private val firebaseAuth: FirebaseAuth,
        private val firebaseFunctions: FirebaseFunctions
) : UserRepository {

    override fun isUserLoggedIn() : Boolean {
        Log.w("UserRepoImpl", firebaseAuth.currentUser?.email ?: "no user logged in")
        return firebaseAuth.currentUser != null
    }

    override fun logInUserEmail(credentials: List<String>) : Observable<String> {
        return Observable.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(credentials[0], credentials[1])
                    .addOnFailureListener {
                        emitter.onNext(it.message)
                    }
                    .addOnSuccessListener {
                        emitter.onNext("logged in")
                    }
        }
    }

    override fun logInUserFacebook() {

    }

    override fun registerUserEmail(credentials: List<String>) : Observable<String> {
        return Observable.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(credentials[1], credentials[2])
                    .addOnSuccessListener {
                        emitter.onNext("logged in")
                    }
                    .addOnFailureListener {
                        emitter.onNext(it.message)
                    }
        }
    }

    override fun logOutUser() {
        firebaseAuth.signOut()
    }

    override fun getUser(): User {
        val user = User()
        val fbUser = firebaseAuth.currentUser
        if (fbUser != null) {
            user.email = fbUser.email.toString()
            user.id = fbUser.uid
        }
        return user
    }

    override fun setFavoriteGenre(genres: List<Int>) : Observable<String> {
        return Observable.create {emitter ->
            emitter.onNext("start")
            firebaseFunctions.getHttpsCallable("users-setFavoriteGenres")
                    .call(genres)
                    .addOnSuccessListener { task ->
                        Log.e("users-setFavoriteGenres", "success")
                        emitter.onNext("success")
                    }
                    .addOnFailureListener {
                        Log.e("users-setFavoriteGenres", it.localizedMessage.toString())
                        emitter.onNext("failed")
                    }
        }
    }

    override fun getAllUsers(): Observable<List<User>> {
        return Observable.create { emitter ->
            firebaseFunctions.getHttpsCallable("users-getAllUsers")
                    .call()
                    .addOnSuccessListener { task ->
                        Log.e("users-getAllUsers", "Success")
                        val result = task.data as ArrayList<*>
                        val list = parseAllUsersToList(result)
                        emitter.onNext(list)
                    }
                    .addOnFailureListener {
                        Log.e("users-getAllUsers error", it.message?: "")
                        //emitter.onNext(listOf(Podcast(title = "failed")))
                    }
        }
    }

    private fun parseAllUsersToList(result: ArrayList<*>) : List<User> {
        val outList = mutableListOf<User>()
        for (user in result) {
            val p = User()
            //val user2 = user as ArrayList<*>
            val map = user as HashMap<*, *>
            p.id = map["uid"].toString()
            p.photoURL = map["photoURL"].toString()
            p.phoneNumber = map["phoneNumber"].toString()
            p.displayName = map["displayName"].toString()
            p.providerId = map["providerId"].toString()
            p.email = map["email"].toString()
            outList.add(p)
        }
        return outList
    }

    override fun addFriend(id: String): Observable<Pair<String, String>> {
        return Observable.create {emitter ->
            firebaseFunctions.getHttpsCallable("social-follow")
                    .call(id)
                    .addOnSuccessListener {
                        Log.e("follow repo", "success")
                        emitter.onNext(id to "following")
                    }
                    .addOnFailureListener {
                        Log.e("follow repo", it.toString())
                        emitter.onNext(id to "not following")
                    }
        }
    }

    override fun unFriend(id: String): Observable<Pair<String, String>> {
        return Observable.create {emitter ->
            firebaseFunctions.getHttpsCallable("social-unfollow")
                    .call(id)
                    .addOnSuccessListener {
                        Log.e("unfollow repo", "success")
                        emitter.onNext(id to "not following")
                    }
                    .addOnFailureListener {
                        Log.e("unfollow repo", it.toString())
                        emitter.onNext(id to "following")
                    }
        }
    }

    override fun isFriend(id: String): Observable<Pair<String, Boolean>> {
        return Observable.create {emitter ->
            //emitter.onNext(Pair(id, false))
            firebaseFunctions.getHttpsCallable("social-doesFollow")
                    .call(id)
                    .addOnSuccessListener {
                        Log.e("doesfollow repo", "success " + it.data as Boolean)
                        emitter.onNext(Pair(id, it.data as Boolean))
                    }
                    .addOnFailureListener {
                        Log.e("doesfollow repo fail", it.toString())
                        emitter.onNext(Pair(id, false))
                    }
        }
    }

    override fun getSocialFeed(): Observable<List<SocialPost>> {
        return Observable.create { emitter ->
                firebaseFunctions.getHttpsCallable("users-getSocialFeed")
                        .call()
                        .addOnSuccessListener { task ->
                            if (task.data != null) {
                                val result = task.data as ArrayList<*>
                                val list = parseSocialFeedToList(result)
                                emitter.onNext(list)
                            }
                        }.addOnFailureListener{
                            Log.e("users-getSocialFeed", it.localizedMessage.toString())
                        }

        }
    }

    private fun parseSocialFeedToList(list: ArrayList<*>) : List<SocialPost> {
        val ret = mutableListOf<SocialPost>()
        for (i in list) {
            val p = SocialPost()
            val map = i as Map<*, *>
            p.bodyText = map["bodyText"].toString()
            p.userId = map["userId"].toString()
            p.user = map["email"].toString()
            p.id = map["post_id"].toString()

            p.likes = (map["likes"] as ArrayList<String>)

            val comments = map["comments"] as ArrayList<*>
            for (c in comments) {
                p.comments.add(Comment(user = "null", text = c.toString()))
            }

            ret.add(p)
        }
        return ret
    }

    override fun createPost(text: String) {
        firebaseFunctions.getHttpsCallable("social-newPost")
                .call(text)

    }

    override fun createComment(postId: String, text: String) {
        val data = mapOf(
                "post" to postId,
                "comment" to text
        )
        firebaseFunctions.getHttpsCallable("social-newComment")
                .call(data)
                .addOnFailureListener {
                    Log.e("createcomment fail", it.localizedMessage.toString())
                }
    }

    override fun likePost(postId: String) {
        firebaseFunctions.getHttpsCallable("social-likePost")
                .call(postId)
                .addOnFailureListener {
                    Log.e("likePost fail", it.localizedMessage.toString())
                }
    }

    override fun unlikePost(postId: String) {
        firebaseFunctions.getHttpsCallable("social-unlikePost")
                .call(postId)
                .addOnFailureListener {
                    Log.e("unlikePost fail", it.localizedMessage.toString())
                }
    }

}