package me.rooshi.podcastapp.feature.main.player

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.base.MyFragment
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.PlayerFragmentBinding
import java.io.IOException

@AndroidEntryPoint
class PlayerFragment : MyFragment(R.layout.player_fragment), PlayerView {

    override val playPauseIntent: Observable<Unit> by lazy { binding.playPause.clicks() }

    private val mediaPlayer = MediaPlayer()

    private val binding by viewBinding(PlayerFragmentBinding::bind)
    private val viewModel : PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //based on info sent from a fragment/main activity, run this again
        //initPlayer("https://locator.simplecastcdn.com/e7ec86c9-5b4f-4c1c-af7b-0957921e175d/dcb5d4e2-c757-4b6b-ae0c-691b26f70e7a.mp3")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.player_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.bindView(this)
    }

    override fun render(state: PlayerState) {
        if (state.playing) {
            binding.playPause.setImageResource(R.drawable.pause)
        } else {
            binding.playPause.setImageResource(R.drawable.play)
        }

        //TODO causes a glitch for some reason
        //binding.playPause.isClickable = state.episodeLoaded

        binding.episodeName.text = state.episodeName
        binding.podcastName.text = state.podcastName
        binding.episodeDate.text = state.episodeDate
    }

    private fun initPlayer(url: String) {
        mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                        .build())
        try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.setOnPreparedListener {
                //initSeekBar()
            }
            mediaPlayer.prepareAsync()
        } catch (e: IOException) {
            Log.e("", "initPlayer: ", e)
        }
    }

/*
    private fun initSeekBar() {
        binding.seekBar.max = mediaPlayer.duration
        val handler = Handler()
        //Make sure you update Seekbar on UI thread
        this.runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer != null && !seeking) {
                    val currentPosition = mediaPlayer.currentPosition
                    binding.seekBar.progress = currentPosition
                    setSeekBarTextViews(mediaPlayer.currentPosition)
                }
                handler.postDelayed(this, 1000)
            }
        })
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                mediaPlayer.seekTo(seekBar.progress)
                mediaPlayer.start()
                seeking = false
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                mediaPlayer.pause()
                seeking = true
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                setSeekBarTextViews(progress)
            }
        })
    }

 */
}