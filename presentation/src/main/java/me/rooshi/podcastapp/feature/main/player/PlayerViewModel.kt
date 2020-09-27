package me.rooshi.podcastapp.feature.main.player

import androidx.hilt.lifecycle.ViewModelInject
import me.rooshi.domain.repository.PlayerRepository
import me.rooshi.podcastapp.common.base.MyViewModel

class PlayerViewModel @ViewModelInject constructor(
        private val playerRepository: PlayerRepository
) : MyViewModel<PlayerView, PlayerState>(PlayerState())