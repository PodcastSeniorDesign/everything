package me.rooshi.podcastapp.feature.main.player

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import me.rooshi.domain.repository.PlayerRepository
import me.rooshi.podcastapp.common.base.MyViewModel

class PlayerViewModel @ViewModelInject constructor(
        private val playerRepository: PlayerRepository
) : MyViewModel<PlayerView, PlayerState>(PlayerState()) {

    override fun bindView(view: PlayerView) {
        super.bindView(view)

        view.playPauseIntent
                .autoDispose(view.scope())
                .subscribe {
                    Log.w("PlayerFragment", "play pause CLICKEDY ICKED")
                }
    }
}