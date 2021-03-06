package me.rooshi.podcastapp.feature.main.subscriptions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jakewharton.rxbinding4.view.ViewScrollChangeEvent
import com.jakewharton.rxbinding4.view.scrollChangeEvents
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.base.MyFragment
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.SubscriptionsFragmentBinding
import javax.inject.Inject

@AndroidEntryPoint
class SubscriptionsFragment constructor(

) : MyFragment(R.layout.subscriptions_fragment), SubscriptionsView {

    @Inject lateinit var subscriptionEpisodeAdapter: SubscriptionEpisodeAdapter

    override val onNewIntentIntent: Subject<Unit> = PublishSubject.create()
    override val bottomScrollReachedIntent: Observable<ViewScrollChangeEvent> by lazy { binding.RV.scrollChangeEvents() }

    private val binding by viewBinding(SubscriptionsFragmentBinding::bind)
    private val viewModel : SubscriptionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e("sub", "oncreate view")
        return inflater.inflate(R.layout.subscriptions_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        onNewIntentIntent.onNext(Unit)
        binding.RV.adapter = subscriptionEpisodeAdapter
        Log.e("sub", "onresume view")
    }

    override fun onStart() {
        super.onStart()
        viewModel.bindView(this)
        onNewIntentIntent.onNext(Unit)

        binding.swipeContainer.setOnRefreshListener {
            onNewIntentIntent.onNext(Unit)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.RV.adapter = subscriptionEpisodeAdapter
        Log.e("sub", "onviewcreated view")
    }

    override fun render(state: SubscriptionsState) {
        subscriptionEpisodeAdapter.data = state.subscriptionEpisodes
        //subscriptionEpisodeAdapter.notifyDataSetChanged()
    }

    override fun startedLoading() {
        binding.swipeContainer.isRefreshing = true
    }

    override fun finishedLoading() {
        binding.swipeContainer.isRefreshing = false
    }

}