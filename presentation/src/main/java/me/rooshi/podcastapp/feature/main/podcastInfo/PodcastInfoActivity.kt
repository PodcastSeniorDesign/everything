package me.rooshi.podcastapp.feature.main.podcastInfo

import dagger.hilt.android.AndroidEntryPoint
import me.rooshi.podcastapp.common.base.MyThemedActivity

@AndroidEntryPoint
class PodcastInfoActivity constructor() : MyThemedActivity(), PodcastInfoView {
    override fun render(state: PodcastInfoState) {

    }

}