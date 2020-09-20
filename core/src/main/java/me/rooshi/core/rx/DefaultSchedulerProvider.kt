package me.rooshi.core.rx

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class DefaultSchedulerProvider : SchedulerProvider {
    override val mainThread: Scheduler
        get() = AndroidSchedulers.mainThread()

    override val io: Scheduler
        get() = Schedulers.io()

    override val newThread: Scheduler
        get() = Schedulers.newThread()
}
