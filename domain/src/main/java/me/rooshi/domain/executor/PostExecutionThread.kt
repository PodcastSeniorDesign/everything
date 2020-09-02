package me.rooshi.domain.executor

import io.reactivex.rxjava3.core.Scheduler

//This interface will be implemented depending on the scheduler we use
// eg. AndroidScheduler.mainThread() for Android and something else for web client etc.
interface PostExecutionThread {
    val scheduler: Scheduler
}