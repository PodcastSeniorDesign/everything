package me.rooshi.podcastapp.feature.main.social.newComment

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.withLatestFrom
import me.rooshi.domain.model.Podcast
import me.rooshi.domain.model.SocialPost
import me.rooshi.domain.repository.UserRepository
import me.rooshi.podcastapp.common.base.MyViewModel
import me.rooshi.podcastapp.feature.main.episodeList.EpisodeItem

class NewCommentViewModel @ViewModelInject constructor(
        private val userRepository: UserRepository,
        private val gson: Gson
        ) : MyViewModel<NewCommentView, NewCommentState>(NewCommentState()) {

    override fun bindView(view: NewCommentView) {
        super.bindView(view)

        view.onNewIntentIntent
                .autoDispose(view.scope())
                .subscribe { intent ->
                    val post = gson.fromJson<SocialPost>(intent.getStringExtra("post"), SocialPost::class.java)
                    newState { copy(post = post) }
                }

        view.textChanges
                .observeOn(AndroidSchedulers.mainThread())
                .autoDispose(view.scope())
                .subscribe { text -> newState { copy(commentText = text.toString()) } }

        view.postComment
                .withLatestFrom(state)
                .autoDispose(view.scope())
                .subscribe {
                    userRepository.createComment(it.second.post.id, it.second.commentText)
                    newState { copy(finished = true) }
                }
    }
}