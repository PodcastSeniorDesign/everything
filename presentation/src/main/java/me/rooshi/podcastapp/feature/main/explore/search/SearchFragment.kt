package me.rooshi.podcastapp.feature.main.explore.search

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.base.MyFragment
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.SearchFragmentBinding

@AndroidEntryPoint
class SearchFragment constructor(

) : MyFragment(R.layout.search_fragment), SearchView {

    private val binding by viewBinding(SearchFragmentBinding::bind)
    private val viewModel : SearchViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewModel.bindView(this)
    }

    override fun render(state: SearchState) {
        TODO("Not yet implemented")
    }
}