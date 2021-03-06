package me.rooshi.podcastapp.feature.main.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.Podcast
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.base.MyFragment
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.ExploreFragmentBinding
import me.rooshi.podcastapp.feature.main.explore.recommendation.RecommendAdapter
import me.rooshi.podcastapp.feature.main.explore.topGenre.GenreRowAdapter
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment constructor(
    //private val someObject: Object
) : MyFragment(R.layout.explore_fragment), ExploreView {

    @Inject lateinit var recommendAdapter: RecommendAdapter
    private val genreAdapter = GenreRowAdapter()

    override val searchIntent by lazy { binding.searchButton.clicks() }
    override val onNewIntentIntent: Subject<Unit> = PublishSubject.create()

    override val topClickIntent: Subject<Podcast> = genreAdapter.clickIntent

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
        binding.genreRV.adapter = genreAdapter
        onNewIntentIntent.onNext(Unit)

        binding.swipeContainer.setOnRefreshListener {
            onNewIntentIntent.onNext(Unit)
        }
    }

    override fun onResume() {
        super.onResume()
        onNewIntentIntent.onNext(Unit)
    }

    override fun render(state: ExploreState) {
        recommendAdapter.data = state.recommendationData
        genreAdapter.data = state.topData
    }

    override fun startedLoading() {
        binding.swipeContainer.isRefreshing = true
    }

    override fun finishedLoading() {
        binding.swipeContainer.isRefreshing = false
    }

    override fun toast(message: String) {
        Toast.makeText(requireView().context, message, Toast.LENGTH_LONG).show()
    }
}