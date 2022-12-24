package com.haris.home

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.haris.base.date.AgenturenDateFormatter
import com.haris.base.date.LocalAgenturenDateFormatter
import com.haris.data.entities.TodoEntity
import com.haris.data.entities.Type
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class LoginTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val entities = listOf(
        TodoEntity(
            id = 1L,
            title = "title",
            "description",
            time = LocalTime.now(),
            date = LocalDate.now(),
            type = Type.Daily
        )
    )

    private val agenturenDateFormatter = AgenturenDateFormatter(
        timeFormatter = {
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault())
        },
        dateFormatter = {
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.getDefault())
        }
    )

    @Test
    fun homeTest() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalAgenturenDateFormatter provides agenturenDateFormatter,
            ) {
                HomeContent(
                    list = flowOf(PagingData.from(entities)).collectAsLazyPagingItems(),
                    navigateToCreate = {},
                    delete = {}
                )
            }
        }

        composeTestRule.onNodeWithText("title").assertIsDisplayed()
        composeTestRule.onNodeWithText("description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Edit").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create").assertIsDisplayed()
    }
}