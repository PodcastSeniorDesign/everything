package me.rooshi.podcastapp.feature.main.social

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import me.rooshi.domain.repository.UserRepository
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyViewModel

class SocialViewModel @ViewModelInject constructor(
    private val navigator: Navigator,
    private val userRepository: UserRepository
) : MyViewModel<SocialView, SocialState>(SocialState()) {

    override fun bindView(view: SocialView) {
        super.bindView(view)

        view.onNewIntentIntent
                .doOnNext {
                    view.startedLoading()
                    newState { copy(posts = listOf()) }
                }
                .switchMap {
                    Log.e("newintent", "feed called")
                    userRepository.getSocialFeed()
                }
                .doOnNext { list ->
                    val itemList = mutableListOf<SocialPostItem>()
                    for (e in list) {
                        itemList.add(SocialPostItem(e))
                    }
                    newState { copy(posts = itemList) }
                }
                .autoDispose(view.scope())
                .subscribe {
                    view.finishedLoading()
                }

        view.newPostIntent
                .autoDispose(view.scope())
                .subscribe {
                    navigator.showNewPostActivity()
                }

        view.addFriendIntent
                .autoDispose(view.scope())
                .subscribe {
                    navigator.showAddFriendsActivity()
                }

        view.likeChangedIntent
                .subscribe {
                    view.NDC()
                }
    }
}