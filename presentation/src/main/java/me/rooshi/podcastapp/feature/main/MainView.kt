package me.rooshi.podcastapp.feature.main

import android.view.MenuItem
import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.common.base.MyView

//an interface View holds all the ReactiveX related variables
// and also the methods that the view can call
interface MainView : MyView<MainState> {

    val castIntent: Observable<Unit>
    val profileIntent: Observable<Unit>
    //val bottomNavigationIntent: Observable<MenuItem>

    fun refreshFragments()
}

//enum class BottomNavItem { SOCIAL, SUBSCRIPTIONS, EXPLORE }