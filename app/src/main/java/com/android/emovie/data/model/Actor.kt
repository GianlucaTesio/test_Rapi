package com.android.emovie.data.model

import com.android.emovie.data.model.db.Actor
import com.google.gson.annotations.SerializedName

data class Actor(
    val id: String,
    val name: String?,
    val biography: String?,
    val birthday: String?,
    val homepage: String?,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    val popularity: Double?,
    @SerializedName("profile_path") val profilePath: String?
) {
    fun getActorDb(): Actor {
        return Actor(
            id,
            name,
            biography,
            birthday,
            homepage,
            placeOfBirth,
            popularity,
            profilePath
        )
    }
}
