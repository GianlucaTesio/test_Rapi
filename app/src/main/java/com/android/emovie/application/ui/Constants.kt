package com.android.emovie.application.ui

interface Constants {
    companion object {
        const val APY_KEY = "fe5f47beb1a48939a88555f819dd88de"
        const val APY_KEY_YOUTUBE = "AIzaSyCgalkGQn_JjtoBMtej9b5vA8CqaMoedPY"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        const val DATABASE_NAME = "e_movies_data"

        const val GLIDE_PLACEHOLDER_ACTOR = "placeholder_actor"
        const val GLIDE_PLACEHOLDER_MOVIE = "placeholder_movie"

        const val FILTER_SPANISH = "es-ES"
        const val FILTER_CURRENT_YEAR = 2022
        const val TOP_RATED_MAX_SIZE_MOVIES = 6
        const val RECOVERY_REQUEST_VIDEO = 1

        // PATH MOVIES
        const val NOW_PLAYING_MOVIES = "now_playing"
        const val TOP_RATED_MOVIES = "top_rated"
        const val UPCOMING_MOVIES = "upcoming"
        const val POPULAR_MOVIES = "popular"

        // KEYS
        const val VIDEO_KEY = "videoKey"
        const val FLOW_KEY = "flow"

        // KEYS VALUES
        const val PERSON_ID_KEY = "personId"
        const val MOVIE_ID_KEY = "movieId"
        const val MOVIES_FLOW = "movies"
        const val FLOW_FAVORITES_MOVIES = "favorites"
    }
}
