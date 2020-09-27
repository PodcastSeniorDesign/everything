package me.rooshi.podcastapp.feature.main.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jakewharton.rxbinding4.view.clicks
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.base.MyFragment
import me.rooshi.podcastapp.databinding.PlayerFragmentBinding
import me.rooshi.podcastapp.feature.main.MainViewModel
import viewBinding

@AndroidEntryPoint
class PlayerFragment : MyFragment(R.layout.player_fragment), PlayerView {

    override val playPauseIntent: Observable<Unit> by lazy { binding.playPause.clicks() }

    private val binding by viewBinding(PlayerFragmentBinding::bind)
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.player_fragment, container, false)
    }

    override fun render(state: PlayerState) {
        if (state.playing) {
            binding.playPause.setImageResource(R.drawable.pause)
        } else {
            binding.playPause.setImageResource(R.drawable.play)
        }
    }
}