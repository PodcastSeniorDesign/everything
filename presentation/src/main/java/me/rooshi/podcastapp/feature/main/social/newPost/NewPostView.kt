package me.rooshi.podcastapp.feature.main.social.newPost

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyView

interface NewPostView : MyView<NewPostState> {
    val onNewIntentIntent: Subject<Unit>
    val postTextChanged: Observable<CharSequence>
    val createPostIntent: Observable<Unit>
}