package com.android.emovie.application.di

import com.android.emovie.data.repositories.MoviesRepositoryInterface
import com.android.emovie.data.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    abstract fun bindMoviesRepository(mainRepository: MoviesRepository): MoviesRepositoryInterface
}
