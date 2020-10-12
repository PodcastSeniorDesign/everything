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
        disposables += playerRepository.currentEpisode
                .distinctUntilChanged()
                .subscribe { e -> newState { copy(episode = e) } }
    }

    override fun bindView(view: PlayerView) {
        super.bindView(view)

        //based on info sent from a fragment/main activity, run this again, and get rid of from here
        //don't have presentation send link, try to have repo have all data and play
        /*playerController.initMediaPlayer("https://locator.simplecastcdn.com/e7ec86c9-5b4f-4c1c-af7b-0957921e175d/dcb5d4e2-c757-4b6b-ae0c-691b26f70e7a.mp3")
                .doOnError { Log.e("initMediaPlayer", it.localizedMessage ?: "no message") }
                .autoDispose(view.scope())
                .subscribe { e ->
                    newState { copy(episodeLoaded = e, episodeLength = playerController.getDuration()) }

                }*/

        view.playPauseIntent
                .withLatestFrom(state) { _, state ->
                    when (state.playing) {
                        true -> playerController.pause()
                        false -> playerController.play()
                    }
                    newState { copy(playing = !playing) }
                }
                .autoDispose(view.scope())
                .subscribe {
                    Log.w("PlayerFragment", "play pause CLICKEDY ICKED")
                }

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
                .autoDispose(view.scope())
                .subscribe {time ->
                    newState { copy(timer = time) }
                }

        view.seekIntent
                .autoDispose(view.scope())
                .subscribe { event ->
                    when (event) {
                        is SeekBarProgressChangeEvent -> {newState { copy(timer = event.progress) }}
                        is SeekBarStartChangeEvent -> {
                            playerController.pause()
                            newState { copy(playing = false) }}
                        is SeekBarStopChangeEvent -> {
                            playerController.seekTo(event.view.progress)
                            playerController.play()
                            newState { copy(playing = true) }}
                    }
                }
    }
}