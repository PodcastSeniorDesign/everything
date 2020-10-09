package me.rooshi.podcastapp.feature.main.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.rooshi.domain.model.Episode
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.base.MyFragment
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.ExploreFragmentBinding
import me.rooshi.podcastapp.feature.main.explore.recommendation.RecommendAdapter
import me.rooshi.podcastapp.feature.main.explore.recommendation.RecommendItem
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment constructor(
    //private val someObject: Object
) : MyFragment(R.layout.explore_fragment), ExploreView {

    @Inject lateinit var recommendAdapter: RecommendAdapter


    private val binding by viewBinding(ExploreFragmentBinding::bind)
    private val viewModel : ExploreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.explore_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.bindView(this)
        binding.recommendRV.adapter = recommendAdapter
    }

    override fun render(state: ExploreState) {
        //recommendAdapter.data = state.recommendationData
        recommendAdapter.data = listOf(RecommendItem(Episode()))
    }
}