package me.rooshi.podcastapp.common

import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import me.rooshi.podcastapp.feature.login.LoginActivity
import me.rooshi.podcastapp.feature.main.explore.search.SearchActivity
import me.rooshi.podcastapp.feature.register.RegisterActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ALL INTENTS THAT MOVE AROUND THE APP SHOULD BE DONE HERE
 *  INCLUDING "SHARE BUTTON CLICKS", EXTERNAL INTENTS
 * THE ONLY EXCEPTIONS ARE THE ONES THAT NEEDS DATA TO BE SENT BACK TO THE ORIGINAL ACTIVITY
 */
@Singleton
class Navigator @Inject constructor(
        @ApplicationContext private val context: Context
) {

    private fun startActivityNewTask(intent: Intent) {
        // This flag will make it so that you can multiple of the same activity on the stack
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    private fun startActivitySingleInstance(intent: Intent) {
        // This flag will make it so that only one of the activity can be made.
        //  If it's already running, then it it brought to the front
        //  use for things like login and register
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    private fun startExternalActivity(intent: Intent) {
        if (intent.resolveActivity(context.packageManager) != null) {
            //we know what type of intent it is
            startActivityNewTask(intent)
        } else {
            //creates a generic chooser so users pick the correct app
            startActivityNewTask(Intent.createChooser(intent, null))
        }
    }

    fun startLoginActivity() {
        val intent = Intent(context, LoginActivity::class.java)
        startActivitySingleInstance(intent)
    }

    fun startRegisterActivity() {
        val intent = Intent(context, RegisterActivity::class.java)
        startActivitySingleInstance(intent)
    }

    fun startSearchActivity() {
        val intent = Intent(context, SearchActivity::class.java)
        startActivitySingleInstance(intent)
    }

    //asking for permissions should also be done here

}