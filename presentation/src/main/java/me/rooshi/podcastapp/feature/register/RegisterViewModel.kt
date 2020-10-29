package me.rooshi.podcastapp.feature.register

import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.withLatestFrom
import me.rooshi.domain.repository.UserRepository
import me.rooshi.podcastapp.common.base.MyViewModel

class RegisterViewModel @ViewModelInject constructor(
        private val userRepository: UserRepository
) : MyViewModel<RegisterView, RegisterState>(RegisterState()) {

    override fun bindView(view: RegisterView) {
        super.bindView(view)

        view.emailChangedIntent
                .observeOn(AndroidSchedulers.mainThread())
                .autoDispose(view.scope())
                .subscribe { text -> newState { copy(emailFilled = text.length >= 5) } }

        view.passwordChangedIntent
                .observeOn(AndroidSchedulers.mainThread())
                .autoDispose(view.scope())
                .subscribe { text -> newState { copy(passwordFilled = text.isNotEmpty()) } }

        view.signInClickedIntent
                .autoDispose(view.scope())
                .subscribe { newState { copy(cancel = true) }}

        view.registerClickedIntent
                .doOnNext {
                    view.closeKeyboard()
                }
                .withLatestFrom(view.emailChangedIntent, view.passwordChangedIntent) { _, email, password ->
                    listOf(email.toString(), password.toString())
                    //newState { copy(loginMessage = "withLatestFrom") }
                }
                .switchMap {
                    userRepository.registerUserEmail(listOf("name", it[0], it[1]))
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