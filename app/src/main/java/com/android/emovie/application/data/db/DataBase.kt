package com.android.emovie.application.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.emovie.data.model.db.Actor
import com.android.emovie.data.model.db.Cast
import com.android.emovie.data.model.db.FavoriteMovie

@Database(entities = [FavoriteMovie::class, Actor::class, Cast::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun moviesDAO(): MoviesDAO
}
