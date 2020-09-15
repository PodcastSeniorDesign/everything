package me.rooshi.podcastapp.feature.main

import me.rooshi.podcastapp.common.base.MyViewModel
import javax.inject.Inject

//THE VIEWMODEL ACTUALLY HAS THE LOGIC FOR EVERYTHING YOU NEED TO DO WHEN YOU WANT TO CONNECT TO THE BACKEND
class MainViewModel @Inject constructor(

) : MyViewModel<MainView, MainState>(MainState()) {

    override fun bindView(view: MainView) {
        super.bindView(view)
    }
}