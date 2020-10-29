package me.rooshi.podcastapp.feature.main.player

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import com.jakewharton.rxbinding4.widget.SeekBarProgressChangeEvent
import com.jakewharton.rxbinding4.widget.SeekBarStartChangeEvent
import com.jakewharton.rxbinding4.widget.SeekBarStopChangeEvent
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.withLatestFrom
import me.rooshi.domain.repository.PlayerRepository
import me.rooshi.podcastapp.common.base.MyViewModel

class PlayerViewModel @ViewModelInject constructor(
        private val playerRepository: PlayerRepository,
        private val playerController: PlayerController
) : MyViewModel<PlayerView, PlayerState>(PlayerState()) {

    init {
        //this doesn't work
        disposables += playerRepository.currentEpisode
                .distinctUntilChanged()
                .filter { e ->
                    e.audioURL.isNotEmpty()
                }
                .doOnNext {e ->
                    newState { copy(episode = e, playing = false) }
                }
                .switchMap { e ->
                    playerController.loadEpisode(e)
                }
                .subscribe { loaded ->
                    newState { copy(episodeLoaded = loaded) } }

    }

    override fun bindView(view: PlayerView) {
        super.bindView(view)

        view.playPauseIntent
                .withLatestFrom(state) { _, state ->
                    when (state.playing) {
                        true -> playerController.pause()
                        false -> playerController.play()
                    }
                    newState { copy(playing = !playing) }
                }
                .autoDispose(view.scope())
                .subscribe()

        view.rewindIntent
                .autoDispose(view.scope())
                .subscribe {
                    playerController.rewind()
                }

        view.forwardIntent
                .autoDispose(view.scope())
                .subscribe {
                    playerController.forward()
                }

        view.timerIntent
                .withLatestFrom(state)
                .filter { !it.second.seeking }
                .autoDispose(view.scope())
                .subscribe {
                    newState { copy(timer = it.first) }
                }

        view.seekIntent
                .autoDispose(view.scope())
                .subscribe { event ->
                    when (event) {
                        is SeekBarProgressChangeEvent -> {
                            newState { copy(timer = event.progress) }
                        }
                        is SeekBarStartChangeEvent -> {
                            playerController.pause()
                            newState { copy(playing = false, seeking = true) }
                        }
                        is SeekBarStopChangeEvent -> {
                            playerController.seekTo(event.view.progress)
                            playerController.play()
                            newState { copy(playing = true, seeking = false) }
                        }
                    }
                }

        view.speedChangeClickIntent
                .autoDispose(view.scope())
                .subscribe {
                    view.showPlaybackSpeedDialog()
                }

        view.speedChangeSubject
                .autoDispose(view.scope())
                .subscribe {
                    playerController.setPlaybackSpeed(it)
                }

    }
}