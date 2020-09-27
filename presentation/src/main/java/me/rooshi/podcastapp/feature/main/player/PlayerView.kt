package me.rooshi.podcastapp.feature.main.player

import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.common.base.MyView

interface PlayerView : MyView<PlayerState>{

    val playPauseIntent: Observable<Unit>

}