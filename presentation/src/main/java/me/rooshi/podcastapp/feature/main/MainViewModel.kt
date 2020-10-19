package me.rooshi.podcastapp.feature.main

import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import me.rooshi.domain.repository.UserRepository
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyViewModel
import javax.inject.Inject

//THE VIEWMODEL ACTUALLY HAS THE LOGIC FOR EVERYTHING YOU NEED TO DO WHEN YOU WANT TO CONNECT TO THE BACKEND
class MainViewModel @ViewModelInject constructor(
        private val navigator : Navigator,
        private val userRepository: UserRepository
        ) : MyViewModel<MainView, MainState>(MainState()) {

    //figure out what disposables are
    //disposables are like async things that you can cancel like http requests etc

    override fun bindView(view: MainView) {
        super.bindView(view)

        //TODO DELETE when done testing
        //TODO move the following non intents to an onNewIntentIntent
        //userRepository.logOutUser()

        if (!userRepository.isUserLoggedIn()) {
            navigator.startLoginActivity()
        }

        view.castIntent
                .autoDispose(view.scope())
                .subscribe { Log.w("CAST INTENT", "AYYYYYYYYY") }

        view.profileIntent
                .autoDispose(view.scope())
                .subscribe { Log.w("Profile INTENT", "replace with settings intent") }
    }
}