package me.rooshi.podcastapp.feature.main.social

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.base.MyFragment
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.SocialFragmentBinding
import me.rooshi.podcastapp.feature.main.subscriptions.SubscriptionsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SocialFragment @Inject constructor(
) : MyFragment(R.layout.social_fragment), SocialView {

    @Inject lateinit var socialPostAdapter: SocialPostAdapter

    override val onNewIntentIntent: Subject<Unit> = PublishSubject.create()
    override val addFriendIntent: Observable<Unit> by lazy { binding.friendFAB.clicks() }
    override val newPostIntent: Observable<Unit> by lazy { binding.postFAB.clicks() }
    override val likeChangedIntent: Subject<Unit> by lazy { socialPostAdapter.likedChangeIntent }

    private val binding by viewBinding(SocialFragmentBinding::bind)
    private val viewModel: SocialViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.social_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.bindView(this)
        binding.RV.adapter = socialPostAdapter
        onNewIntentIntent.onNext(Unit)

        binding.swipeContainer.setOnRefreshListener {
            onNewIntentIntent.onNext(Unit)
        }
    }

    override fun render(state: SocialState) {
        socialPostAdapter.data = state.posts
        socialPostAdapter.notifyDataSetChanged()
    }

    override fun startedLoading() {
        binding.swipeContainer.isRefreshing = true
    }

    override fun finishedLoading() {
        binding.swipeContainer.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        onNewIntentIntent.onNext(Unit)
    }

    override fun NDC() {
        socialPostAdapter.notifyDataSetChanged()
    }

}