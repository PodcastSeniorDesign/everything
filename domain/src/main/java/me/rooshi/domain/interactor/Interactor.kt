package me.rooshi.domain.interactor

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers


abstract class Interactor<in Params> : Disposable {

    private val disposables: CompositeDisposable = CompositeDisposable()

    abstract fun buildObservable(params: Params): Flowable<*>

    fun execute(params: Params, onComplete: () -> Unit = {}) {
        disposables += buildObservable(params)
                .subscribeOn(Schedulers.io())
                //technically its bad form to have any reference to Android in the domain layer
                //  but I can't find a solution that works and we're running out of time
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(onComplete)
                .subscribe()
    }

    override fun dispose() {
        return disposables.dispose()
    }

    override fun isDisposed(): Boolean {
        return disposables.isDisposed
    }
}