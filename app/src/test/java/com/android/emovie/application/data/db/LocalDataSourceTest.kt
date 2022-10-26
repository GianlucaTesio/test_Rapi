package com.android.emovie.application.data.db

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.android.emovie.data.model.db.FavoriteMovie
import com.android.emovie.util.ObjectMocks.getActor
import com.android.emovie.util.ObjectMocks.getFavoriteMovie
import com.android.emovie.util.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@SmallTest
class LocalDataSourceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DataBase
    private lateinit var dao: MoviesDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DataBase::class.java
        ).allowMainThreadQueries().build()
        dao = database.newsDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testDao_insertFavoriteNewsMovie_insertSuccess() = runBlockingTest {
        val movie = getFavoriteMovie()
        dao.insertFavoriteMovies(movie)
        val favoriteNews = dao.getAllFavorites().getOrAwaitValueTest()

        Assert.assertTrue(favoriteNews.contains(movie))
    }

    @Test
    fun testDao_existFavoriteMovie_insertSuccess() = runBlockingTest {
        val movie = getFavoriteMovie()
        dao.insertFavoriteMovies(movie)
        val exist = dao.checkFavoriteExists(movie.id)

        Assert.assertEquals(1, exist)
    }

    @Test
    fun testDao_removeFavoriteMovie_removeSuccess() = runBlockingTest {
        val movie = getFavoriteMovie()
        dao.insertFavoriteMovies(movie)
        dao.removeFavorite(movie.id)

        Assert.assertTrue(dao.getAllFavorites().getOrAwaitValueTest().isEmpty())
    }

    @Test
    fun testDao_insertAndGetActor_insertAndGetSuccess() = runBlockingTest {
        val actor = getActor()
        dao.insertActor(actor)
        val actorDB = dao.getActor(actor.id)

        Assert.assertNotNull(actorDB)
    }
}