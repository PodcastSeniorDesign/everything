package me.rooshi.podcastapp.feature.main.social.addFriend

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyView

interface AddFriendView : MyView<AddFriendState> {
    val onNewIntentIntent: Subject<Unit>
    val finishedIntent: Observable<Unit>
    val toggleFriendshipIntent: Observable<AddFriendItem>
    val isFriendIntent: Observable<AddFriendItem>

    fun checkFriendship(item: AddFriendItem)
}