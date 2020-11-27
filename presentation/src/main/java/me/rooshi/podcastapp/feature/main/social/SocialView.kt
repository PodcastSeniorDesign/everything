package me.rooshi.podcastapp.feature.main.social

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyView

interface SocialView : MyView<SocialState> {
    val onNewIntentIntent: Subject<Unit>
    val addFriendIntent: Observable<Unit>
    val newPostIntent: Observable<Unit>
    val likeChangedIntent: Subject<Unit>

    fun startedLoading()
    fun finishedLoading()
    fun NDC()
}