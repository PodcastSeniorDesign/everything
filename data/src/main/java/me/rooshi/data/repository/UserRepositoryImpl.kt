package me.rooshi.data.repository

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
        private var firebaseUser: FirebaseUser,
        private val firebaseAuth: FirebaseAuth
) : UserRepository {

    override fun isUserLoggedIn() {
        TODO("Not yet implemented")
    }

    override fun logInUserEmail(credentials: List<String>) : Single<String> {
        return Single.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(credentials[0], credentials[1])
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            firebaseUser = firebaseAuth.currentUser!!
                        }
                        emitter.onSuccess(task.result.toString())
                    }
        }
    }

    override fun registerUser() {
        TODO("Not yet implemented")
    }

    override fun getUser(): User? {
        TODO("Not yet implemented")
    }

}