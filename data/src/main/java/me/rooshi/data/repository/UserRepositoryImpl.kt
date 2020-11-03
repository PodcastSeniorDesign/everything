package me.rooshi.data.repository

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.functions.FirebaseFunctions
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import me.rooshi.domain.model.Podcast
import me.rooshi.domain.model.PodcastInfoResult
import me.rooshi.domain.model.User
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

    //somehow convert a firebase user to waveform user
    //need to save the info too
    override fun getUser(): User? {
        //return firebaseAuth.currentUser
        TODO()
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
            val user2 = user as ArrayList<*>
            val map = user2[0] as HashMap<*, *>
            p.id = map["uid"].toString()
            p.photoURL = map["photoURL"].toString()
            p.phoneNumber = map["phoneNumber"].toString()
            p.displayName = map["displayName"].toString()
            p.providerId = map["providerId"].toString()
            outList.add(p)
        }
        return outList
    }

}