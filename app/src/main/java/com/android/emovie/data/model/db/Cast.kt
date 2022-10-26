package com.android.emovie.data.model.db

import androidx.room.Entity

@Entity(tableName = "cast_movie", primaryKeys = ["id", "idActor"])
data class Cast(
    val id: String,
    val idActor: String
)
