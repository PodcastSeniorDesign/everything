package me.rooshi.podcastapp.feature.main.social

import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.common.base.MyView

interface SocialView : MyView<SocialState> {
    val addFriendIntent: Observable<Unit>
    val createPostIntent: Observable<Unit>
}