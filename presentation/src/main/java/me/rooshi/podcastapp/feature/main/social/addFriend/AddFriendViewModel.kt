package me.rooshi.podcastapp.feature.main.social.addFriend

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.kotlin.withLatestFrom
import me.rooshi.domain.repository.UserRepository
import me.rooshi.podcastapp.common.base.MyViewModel

class AddFriendViewModel @ViewModelInject constructor(
        private val userRepository: UserRepository
) : MyViewModel<AddFriendView, AddFriendState>(AddFriendState()) {

    override fun bindView(view: AddFriendView) {
        super.bindView(view)

        view.onNewIntentIntent
                .switchMap {
                    userRepository.getAllUsers()
                }
                .autoDispose(view.scope())
                .subscribe {
                    val itemList = mutableListOf<AddFriendItem>()
                    for (i in it) {
                        itemList.add(AddFriendItem(i))
                    }
                    newState { copy(users = itemList) }
                }

        view.addFriendCheckedIntent
                .withLatestFrom(state)
                .autoDispose(view.scope())
                .subscribe {

                }

        view.finishedIntent
                .autoDispose(view.scope())
                .subscribe {

                }
    }

}