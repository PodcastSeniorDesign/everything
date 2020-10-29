package me.rooshi.podcastapp.feature.login

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import me.rooshi.podcastapp.common.base.MyView
import javax.security.auth.Subject

interface LoginView : MyView<LoginState> {

    val emailChangedIntent: Observable<CharSequence>
    val passwordChangedIntent: Observable<CharSequence>
    val signInClickedIntent: Observable<Unit>
    val registerClickedIntent: Observable<Unit>

    fun closeKeyboard()
}