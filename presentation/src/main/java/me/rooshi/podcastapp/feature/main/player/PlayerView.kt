package me.rooshi.podcastapp.feature.main.player

import com.jakewharton.rxbinding4.widget.SeekBarChangeEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyView

interface PlayerView : MyView<PlayerState>{

    val playPauseIntent: Observable<Unit>
    val rewindIntent: Observable<Unit>
    val forwardIntent: Observable<Unit>
    val timerIntent: Observable<Int>
    val seekIntent: Observable<SeekBarChangeEvent>
    val speedChangeClickIntent: Observable<Unit>
    val speedChangeSubject: Subject<String>

    fun showPlaybackSpeedDialog()
}