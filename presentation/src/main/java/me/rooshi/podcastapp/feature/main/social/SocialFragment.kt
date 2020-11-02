package me.rooshi.podcastapp.feature.main.social

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
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
    }

    override fun render(state: SocialState) {
        socialPostAdapter.data = state.posts
        socialPostAdapter.notifyDataSetChanged()
    }

}