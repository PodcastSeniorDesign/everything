package me.rooshi.podcastapp.feature.main.social.newComment

import android.content.Intent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyView

interface NewCommentView : MyView<NewCommentState> {
    val onNewIntentIntent: Subject<Intent>
    val textChanges: Observable<CharSequence>
    val postComment: Observable<Unit>
}