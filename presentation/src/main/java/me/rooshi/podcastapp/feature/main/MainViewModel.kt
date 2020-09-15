package me.rooshi.podcastapp.feature.main

import android.util.Log
import android.widget.Toast
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import me.rooshi.podcastapp.common.base.MyViewModel
import javax.inject.Inject

//THE VIEWMODEL ACTUALLY HAS THE LOGIC FOR EVERYTHING YOU NEED TO DO WHEN YOU WANT TO CONNECT TO THE BACKEND
class MainViewModel @Inject constructor(

) : MyViewModel<MainView, MainState>(MainState()) {

    //figure out what disposables are

    override fun bindView(view: MainView) {
        super.bindView(view)

        view.castIntent
                .autoDispose(view.scope())
                .subscribe { Log.w("CAST INTENT", "AYYYYYYYYY") }
    }
}