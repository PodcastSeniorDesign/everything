package me.rooshi.podcastapp.feature.main.explore.search

import com.jakewharton.rxbinding4.widget.TextViewEditorActionEvent
import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.common.base.MyView

interface SearchView : MyView<SearchState> {
    val queryChangedIntent: Observable<CharSequence>
    val searchIntent: Observable<TextViewEditorActionEvent>
}