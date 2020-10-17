package me.rooshi.podcastapp.common.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import autodispose2.androidx.lifecycle.autoDispose
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

abstract class MyViewModel<in View: MyView<State>, State>(initialState: State) : ViewModel() {

    protected val disposables = CompositeDisposable()
    protected val state: Subject<State> = BehaviorSubject.createDefault(initialState)

    private val stateReducer: Subject<State.() -> State> = PublishSubject.create()

    init {
        disposables += stateReducer
                .observeOn(AndroidSchedulers.mainThread())
                .scan(initialState) { state, reducer -> reducer(state) }
                .subscribe(state::onNext)
    }

    @CallSuper
    open fun bindView(view: View) {
        state
                .observeOn(AndroidSchedulers.mainThread())
                .autoDispose(view.scope())
                .subscribe(view::render)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    protected fun newState(reducer: State.() -> State) = stateReducer.onNext(reducer)
}