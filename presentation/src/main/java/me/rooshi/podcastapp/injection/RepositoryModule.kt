package me.rooshi.podcastapp.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import me.rooshi.data.repository.UserRepositoryImpl
import me.rooshi.domain.repository.UserRepository

@InstallIn(ActivityComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}