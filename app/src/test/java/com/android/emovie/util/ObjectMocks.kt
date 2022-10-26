package com.android.emovie.util

import com.android.emovie.data.model.db.Actor
import com.android.emovie.data.model.db.FavoriteMovie

object ObjectMocks {

    fun getFavoriteMovie(): FavoriteMovie {
        return FavoriteMovie(
            "1234", "Title", "24/11/2022", 24.5, 2,
            "override", 2, 5, "posterPath"
        )
    }

    fun getActor(): Actor {
        return Actor(
            "1234", "Name", "biography", "24/11/1979",
            "homepage", "London", 2.3, "profile"
        )
    }
}