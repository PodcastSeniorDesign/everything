package me.rooshi.podcastapp.feature.favoritegenre

import androidx.hilt.lifecycle.ViewModelInject
import me.rooshi.podcastapp.common.base.MyViewModel

class FavoriteGenreViewModel @ViewModelInject constructor(

) : MyViewModel<FavoriteGenreView, FavoriteGenreState>(FavoriteGenreState()) {
    override fun bindView(view: FavoriteGenreView) {
        super.bindView(view)
    }
}