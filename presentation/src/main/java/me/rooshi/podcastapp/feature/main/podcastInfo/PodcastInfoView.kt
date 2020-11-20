package me.rooshi.podcastapp.feature.main.podcastInfo

import android.content.Intent
import com.jakewharton.rxbinding4.view.ViewScrollChangeEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyView

interface PodcastInfoView : MyView<PodcastInfoState> {
    val onNewIntentIntent: Subject<Intent>
    val bottomScrollReachedIntent: Observable<ViewScrollChangeEvent>
    val subscribeIntent: Observable<Unit>

    fun startedLoading()
    fun finishedLoading()

}