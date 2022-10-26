package com.android.emovie.application.di

import android.content.Context
import androidx.room.Room
import com.android.emovie.application.data.api.MovieApiService
import com.android.emovie.application.data.db.DataBase
import com.android.emovie.application.ui.Constants.Companion.BASE_URL
import com.android.emovie.application.ui.Constants.Companion.DATABASE_NAME
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            DataBase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMoviesDAO(db: DataBase) = db.moviesDAO()

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): MovieApiService = retrofit.create(MovieApiService::class.java)
}
