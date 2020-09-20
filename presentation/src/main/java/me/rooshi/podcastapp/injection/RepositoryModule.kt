package me.rooshi.podcastapp.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.rooshi.data.repository.UserRepositoryImpl
import me.rooshi.domain.repository.UserRepository

@InstallIn(ApplicationComponent::class)
@Module
abstract class RepositoryModule  {
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository
}