package me.rooshi.podcastapp.feature.favoritegenre

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.FavoriteActivityBinding
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteGenreActivity : MyThemedActivity(), FavoriteGenreView  {

    @Inject lateinit var genreAdapter: GenreAdapter

    private val binding by viewBinding(FavoriteActivityBinding::inflate)
    private val viewModel: FavoriteGenreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        viewModel.bindView(this)

        binding.genreRV.adapter = genreAdapter
    }

    override fun render(state: FavoriteGenreState) {
        genreAdapter.data = state.genres
    }
}