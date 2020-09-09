package me.rooshi.core.rx

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProvider {
    val mainThread: Scheduler
    val io: Scheduler
    val newThread: Scheduler
}