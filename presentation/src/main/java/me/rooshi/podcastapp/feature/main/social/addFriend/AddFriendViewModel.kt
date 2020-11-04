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
                .doOnNext {
                    val itemList = mutableListOf<AddFriendItem>()
                    for (i in it) {
                        val item = AddFriendItem(i)
                        itemList.add(item)
                    }
                    newState { copy(users = itemList) }
                }
                .withLatestFrom(state)
                .doOnNext {
                    //for (i in it.second.users) {
                    //view.checkFriendship(i)
                    //}
                }
                .autoDispose(view.scope())
                .subscribe {

                }

        view.toggleFriendshipIntent
                .withLatestFrom(state)
                .switchMap {
                    if (it.first.status) {
                        //unfriend
                        userRepository.unFriend(it.first.user.id)
                    } else {
                        //friend
                        userRepository.addFriend(it.first.user.id)
                    }
                }
                .withLatestFrom(state)
                .autoDispose(view.scope())
                .subscribe {
                    val users = it.second.users
                    for (u in users) {
                        if (u.user.id == it.first.first) {
                            u.status = it.first.second == "following"
                        }
                    }
                    newState { copy(users = users) }
                }

        view.isFriendIntent
                .switchMap {
                    Log.e("isfriendintent", "called")
                    userRepository.isFriend(it.user.id)
                }
                .withLatestFrom(state)
                .autoDispose(view.scope())
                .subscribe {
                    for (item in it.second.users) {
                        if (item.user.id == it.first.first) {
                            item.status = it.first.second
                            Log.e("subscribe", it.first.second.toString())
                        }
                    }
                    newState { copy(users = it.second.users, notCheckedFriendship = false) }
                }

        view.finishedIntent
                .autoDispose(view.scope())
                .subscribe {
                    newState { copy(finished = true) }
                }
    }

}