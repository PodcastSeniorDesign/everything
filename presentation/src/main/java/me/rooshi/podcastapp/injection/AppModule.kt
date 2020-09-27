package me.rooshi.podcastapp.injection

import androidx.lifecycle.ViewModelProvider
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.rooshi.data.repository.UserRepositoryImpl
import me.rooshi.domain.repository.UserRepository
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    /*
    @Singleton
    @Provides
    fun provideSomeObject(): Object {
        return Object()
    }
    */

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideCallbackManager(): CallbackManager {
        return CallbackManager.Factory.create()
    }

}