package com.android.emovie.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.emovie.data.model.Actor

@Entity(tableName = "actors")
data class Actor(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "biography")
    val biography: String?,
    @ColumnInfo(name = "birthday")
    val birthday: String?,
    @ColumnInfo(name = "homepage")
    val homepage: String?,
    @ColumnInfo(name = "placeOfBirth")
    val placeOfBirth: String?,
    @ColumnInfo(name = "popularity")
    val popularity: Double?,
    @ColumnInfo(name = "profilePath")
    val profilePath: String?
) {
    fun getActor(): Actor {
        return Actor(
            id = id,
            name = name,
            biography = biography,
            birthday = birthday,
            homepage = homepage,
            placeOfBirth = placeOfBirth,
            popularity = popularity,
            profilePath = profilePath
        )
    }
}
