package me.rooshi.podcastapp.feature.login

import android.util.Log
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.withLatestFrom
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.domain.interactor.LogInUserEmail
import me.rooshi.domain.repository.UserRepository
import me.rooshi.podcastapp.common.base.MyViewModel
import javax.inject.Inject

class LoginViewModel @ViewModelInject constructor(
        private val userRepository: UserRepository
) : MyViewModel<LoginView, LoginState>(LoginState()) {

    override fun bindView(view: LoginView) {
        super.bindView(view)

        view.emailChangedIntent
                .observeOn(AndroidSchedulers.mainThread())
                .autoDispose(view.scope())
                .subscribe { text -> newState { copy(emailFilled = text.length >= 5) } }

        view.passwordChangedIntent
                .observeOn(AndroidSchedulers.mainThread())
                .autoDispose(view.scope())
                .subscribe { text -> newState { copy(passwordFilled = text.isNotEmpty()) } }

        view.registerClickedIntent
                .autoDispose(view.scope())
                .subscribe { newState { copy(registering = true) }}

        //interactor version
        /* view.signInClickedIntent
                 .withLatestFrom(view.emailChangedIntent, view.passwordChangedIntent) {
                     _, email, password->
                     val params = listOf<String>(email.toString(), password.toString())
                     logInUserEmail.execute(params)
                 }
                 .autoDispose(view.scope())
                 .subscribe()*/

        //normal call

        view.signInClickedIntent
                .doOnNext {
                    view.closeKeyboard()
                }
                .withLatestFrom(view.emailChangedIntent, view.passwordChangedIntent) { _, email, password ->
                    listOf(email.toString(), password.toString())
                    //newState { copy(loginMessage = "withLatestFrom") }
                }
                .switchMap {
                    userRepository.logInUserEmail(listOf(it[0], it[1]))
                }
                .autoDispose(view.scope())
                .subscribe {
                    if (it == "logged in") {
                        newState { copy(loginMessage = "", loggedIn = true) }
                    } else {
                        newState { copy(loginMessage = it, loggedIn = false) }
                    }
                }

    }

}