package me.rooshi.podcastapp.feature.main.social

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyViewModel

class SocialViewModel @ViewModelInject constructor(
    private val navigator: Navigator
) : MyViewModel<SocialView, SocialState>(SocialState()) {

    override fun bindView(view: SocialView) {
        super.bindView(view)

        view.addFriendIntent
                .autoDispose(view.scope())
                .subscribe {
                    Log.e("addfreiend", "socvm")
                    navigator.showAddFriendsList()
                }
    }
}