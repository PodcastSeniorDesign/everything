package me.rooshi.podcastapp.common.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import autodispose2.androidx.lifecycle.autoDispose
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

abstract class MyViewModel<in View: MyView<State>, State>(initialState: State) : ViewModel() {

    protected val state: Subject<State> = BehaviorSubject.createDefault(initialState)

    @CallSuper
    open fun bindView(view: View) {
        state
                .observeOn(AndroidSchedulers.mainThread())
                .autoDispose(view.scope())
                .subscribe(view::render)
    }
}