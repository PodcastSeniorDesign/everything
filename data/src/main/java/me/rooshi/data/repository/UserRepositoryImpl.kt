package me.rooshi.data.repository

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import me.rooshi.domain.model.User
import me.rooshi.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
        private val firebaseAuth: FirebaseAuth
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

    override fun registerUser() {
        TODO("Not yet implemented")
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

}