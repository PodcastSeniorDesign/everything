package me.rooshi.podcastapp.feature.favoriteGenre

import android.os.Bundle
import androidx.activity.viewModels
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.FavoriteActivityBinding

@AndroidEntryPoint
class FavoriteGenreActivity : MyThemedActivity(), FavoriteGenreView  {

    private val genreAdapter = FavoriteGenreAdapter()

    override val finishedIntent: Observable<Unit> by lazy { binding.finishedButton.clicks() }
    override val favoriteGenreCheckedIntent: Observable<FavoriteGenreItem> = genreAdapter.favoriteGenreItemClick

    private val binding by viewBinding(FavoriteActivityBinding::inflate)
    private val viewModel: FavoriteGenreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        viewModel.bindView(this)

        binding.genreRV.adapter = genreAdapter
    }

    override fun render(state: FavoriteGenreState) {
        if (state.finished) {
            finish()
        }

        genreAdapter.data = state.genres
    }

    override fun dataSetChanged() {
        genreAdapter.notifyDataSetChanged()
    }
}