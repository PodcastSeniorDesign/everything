package me.rooshi.podcastapp.feature.register

import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.common.base.MyView

interface RegisterView : MyView<RegisterState> {
    val emailChangedIntent: Observable<CharSequence>
    val passwordChangedIntent: Observable<CharSequence>
    val signInClickedIntent: Observable<Unit>
    val registerClickedIntent: Observable<Unit>

    fun closeKeyboard()
}