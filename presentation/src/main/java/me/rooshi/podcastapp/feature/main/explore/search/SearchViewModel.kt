package me.rooshi.podcastapp.feature.main.explore.search

import androidx.hilt.lifecycle.ViewModelInject
import me.rooshi.podcastapp.common.base.MyViewModel

class SearchViewModel @ViewModelInject constructor(

) : MyViewModel<SearchView, SearchState>(SearchState()) {

}