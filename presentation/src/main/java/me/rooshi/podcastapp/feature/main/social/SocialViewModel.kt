package me.rooshi.podcastapp.feature.main.social

import androidx.hilt.lifecycle.ViewModelInject
import me.rooshi.podcastapp.common.base.MyViewModel

class SocialViewModel @ViewModelInject constructor(

) : MyViewModel<SocialView, SocialState>(SocialState()) {

}