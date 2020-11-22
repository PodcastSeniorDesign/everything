package me.rooshi.podcastapp.feature.main.explore.search

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.kotlin.withLatestFrom
import me.rooshi.domain.repository.PodcastRepository
import me.rooshi.podcastapp.common.base.MyViewModel

class SearchViewModel @ViewModelInject constructor(
    private val podcastRepository: PodcastRepository
) : MyViewModel<SearchView, SearchState>(SearchState()) {

    override fun bindView(view: SearchView) {
        super.bindView(view)

        view.queryChangedIntent
                .autoDispose(view.scope())
                .subscribe { newState { copy(searchTerm = it.toString()) } }

        view.searchIntent
                .doOnNext {
                    view.closeKeyboard()
                    view.startedLoading()
                }
                .withLatestFrom(view.queryChangedIntent) { _, query ->
                    query
                }
                .switchMap {
                    podcastRepository.searchPodcasts(it.toString())
                }
                .autoDispose(view.scope())
                .subscribe { list ->
                    Log.e("search icon on keyboard", "CLICKEDDDDDDDDDD!!!!(@io&)")

                    val itemList = mutableListOf<SearchItem>()
                    for (p in list) {
                        itemList.add(SearchItem(p))
                    }
                    newState { copy(results = itemList) }
                    view.finishedLoading()
                }
    }
}