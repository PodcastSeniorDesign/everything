package me.rooshi.podcastapp.feature.main.player

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import java.io.IOException

class PlayerController constructor(
) {

    private val mediaPlayer = MediaPlayer()

    val timer : Subject<Int> = BehaviorSubject.create()

    init {
        setMediaPlayerAttributes()
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                    timer.onNext(mediaPlayer.currentPosition)
                    handler.postDelayed(this, 1000)
            }
        })
    }

    fun setMediaPlayerAttributes() {
        mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                        .build())
    }


    fun initMediaPlayer(url: String) : Observable<Boolean> {
        return Observable.create { emitter ->
            emitter.onNext(false)
            try {
                mediaPlayer.setDataSource(url)

                mediaPlayer.setOnPreparedListener {
                    emitter.onNext(true)
                }
                mediaPlayer.prepareAsync()
            } catch (e: IOException) {
                emitter.onError(e)
            }
        }
    }

    fun play() {
        mediaPlayer.start()
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun rewind() {
        mediaPlayer.seekTo(mediaPlayer.currentPosition - 15 * 1000)
    }

    fun forward() {
        mediaPlayer.seekTo(mediaPlayer.currentPosition + 15 * 1000)
    }

    fun seekTo(millis: Int) {
        mediaPlayer.seekTo(millis)
        play()
    }

    fun getDuration() : Int {
        return mediaPlayer.duration
    }
}