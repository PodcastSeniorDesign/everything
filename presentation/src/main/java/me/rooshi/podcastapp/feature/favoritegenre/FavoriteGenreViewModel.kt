package me.rooshi.podcastapp.feature.favoritegenre

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.kotlin.withLatestFrom
import me.rooshi.domain.repository.UserRepository
import me.rooshi.podcastapp.common.base.MyViewModel

class FavoriteGenreViewModel @ViewModelInject constructor(
        private val userRepository: UserRepository
) : MyViewModel<FavoriteGenreView, FavoriteGenreState>(FavoriteGenreState()) {

    override fun bindView(view: FavoriteGenreView) {
        super.bindView(view)

        view.favoriteGenreCheckedIntent
                .withLatestFrom(state)
                .autoDispose(view.scope())
                .subscribe {
                    for (g in it.second.genres) {
                        if (g.genre.id == it.first.genre.id) {
                            g.clicked = !g.clicked
                        }
                    }
                }

        view.finishedIntent
                .withLatestFrom(state)
                .switchMap {
                    val clickedList = mutableListOf<Int>()
                    for (g in it.second.genres) {
                        if (g.clicked) clickedList.add(g.genre.id)
                    }
                    userRepository.setFavoriteGenre(clickedList)
                }
                .autoDispose(view.scope())
                .subscribe {
                    Log.e("viewmodel", it)
                    if (it == "success") {
                        newState { copy(finished = true) }
                    }
                }
    }

}