package me.rooshi.podcastapp.feature.main.social.newPost

import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.withLatestFrom
import me.rooshi.domain.repository.UserRepository
import me.rooshi.podcastapp.common.base.MyViewModel

class NewPostViewModel @ViewModelInject constructor(
        private val userRepository: UserRepository
) : MyViewModel<NewPostView, NewPostState>(NewPostState()) {

    override fun bindView(view: NewPostView) {
        super.bindView(view)

        view.postTextChanged
                .observeOn(AndroidSchedulers.mainThread())
                .autoDispose(view.scope())
                .subscribe {
                    newState { copy(postText = it.toString()) }
                }


        view.createPostIntent
                .withLatestFrom(state)
                .doOnNext {
                    userRepository.createPost(it.second.postText)
                }
                .autoDispose(view.scope())
                .subscribe {
                    newState { copy(finished = true) }
                }
    }

}