package me.rooshi.podcastapp.feature.favoritegenre

import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.common.base.MyView

interface FavoriteGenreView : MyView<FavoriteGenreState> {
    val finishedIntent: Observable<Unit>
    val favoriteGenreCheckedIntent: Observable<FavoriteGenreItem>

    fun dataSetChanged()
}