package com.android.emovie.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.emovie.data.model.Movie

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey
    val id : String,
    @ColumnInfo(name = "title")
    val title : String,
    @ColumnInfo(name = "releaseDate")
    val releaseDate : String?,
    @ColumnInfo(name = "voteAverage")
    val voteAverage : Double?,
    @ColumnInfo(name = "runtime")
    val runtime : Int?,
    @ColumnInfo(name = "overview")
    val overview : String?,
    @ColumnInfo(name = "budget")
    val budget : Long?,
    @ColumnInfo(name = "revenue")
    val revenue : Long?,
    @ColumnInfo(name = "posterPath")
    val posterPath : String?
){
    fun getMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            runtime = runtime,
            overview = overview,
            budget = budget,
            revenue = revenue,
            posterPath = posterPath
        )
    }
}