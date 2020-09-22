package me.rooshi.podcastapp.feature.login

import android.util.Log
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.withLatestFrom
import io.reactivex.rxjava3.schedulers.Schedulers
import me.rooshi.domain.interactor.LogInUserEmail
import me.rooshi.domain.repository.UserRepository
import me.rooshi.podcastapp.common.base.MyViewModel
import javax.inject.Inject

class LoginViewModel @ViewModelInject constructor(
        private val userRepository: UserRepository,
        private val logInUserEmail: LogInUserEmail,
        @Assisted private val savedStateHandle: SavedStateHandle
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

        view.signInClickedIntent
                .withLatestFrom(view.emailChangedIntent, view.passwordChangedIntent) {
                    _, email, password->
                    val params = listOf<String>(email.toString(), password.toString())
                    logInUserEmail.execute(params)
                }
                .map { _ ->  }

    }

}