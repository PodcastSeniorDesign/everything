package me.rooshi.podcastapp.feature.login

import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.withLatestFrom
import me.rooshi.domain.interactor.LogInUserEmail
import me.rooshi.podcastapp.common.base.MyViewModel
import javax.inject.Inject

class LoginViewModel  @Inject constructor(
    private val logInUserEmail: LogInUserEmail
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
                .subscribe { text -> newState { copy(passwordFilled = text.length > 1) } }

        view.signInClickedIntent
                .observeOn(AndroidSchedulers.mainThread())
                .withLatestFrom(view.emailChangedIntent, view.passwordChangedIntent) {_, email, password ->
                    logInUserEmail.execute(listOf(email.toString(), password.toString()))
                }
                .autoDispose(view.scope())
                .subscribe {} //this is the result*** and what you do with it

    }

}