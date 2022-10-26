package com.android.emovie.util

import com.android.emovie.data.model.db.FavoriteMovie

object ObjectMocks {

    fun getFavoriteMovie(): FavoriteMovie {
        return FavoriteMovie(
            "1234", "Title", "24/11/2022", 24.5, 2,
            "override", 2, 5, "posterPath"
        )
    }
}