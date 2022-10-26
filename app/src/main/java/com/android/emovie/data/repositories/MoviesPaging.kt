package com.android.emovie.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.emovie.application.data.api.NetworkDataSource
import com.android.emovie.data.model.Movie
import retrofit2.HttpException
import java.io.IOException

class MoviesPaging(private val networkDataSource: NetworkDataSource, private val path: String) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val response = networkDataSource.getMovies(path, nextPage)

            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.results.isEmpty()) null else nextPage + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}