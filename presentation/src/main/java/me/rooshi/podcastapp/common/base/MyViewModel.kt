package me.rooshi.podcastapp.common.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


abstract class MyViewModel<in View: MyView<State>, State>(initialState: State) : ViewModel() {

    protected val state: Subject<State> = BehaviorSubject.createDefault(initialState)

    @CallSuper
    open fun bindView(view: View) {
        state
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(view.scope())
                .subscribe(view::render)
    }
}