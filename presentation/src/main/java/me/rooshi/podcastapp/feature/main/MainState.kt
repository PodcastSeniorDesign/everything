package me.rooshi.podcastapp.feature.main

//A "State" class holds the data making up the view aka state
// and also its other types of state depending on what the application is doing
//This holds all the variables that aren't related to ReactiveX
data class MainState(
        //if the state doesn't make sense then set to true
        val hasError: Boolean = false,

        val loggedIn: Boolean = false
) {


}