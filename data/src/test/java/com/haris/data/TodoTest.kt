package com.haris.data

import com.haris.data.daos.TodoDao
import com.haris.data.entities.TodoEntity
import com.haris.data.entities.Type
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import java.time.OffsetDateTime
import javax.inject.Inject

@UninstallModules
@HiltAndroidTest
class EpisodesTest : DatabaseTest() {
    @Inject
    lateinit var database: AgenturenDb

    @Inject
    lateinit var todoDao: TodoDao

    private val entity = TodoEntity(1L, "title", "desc", OffsetDateTime.now(), Type.Daily)

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            database.todoDao().insert(entity)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insert() = runTest {
        database.todoDao().insert(entity)
        MatcherAssert.assertThat(todoDao.getItem(1L), `is`(entity))
    }
}
