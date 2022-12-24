package com.haris.create

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.haris.base.date.AgenturenDateFormatter
import com.haris.base.date.LocalAgenturenDateFormatter
import org.junit.Rule
import org.junit.Test
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class LoginTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val agenturenDateFormatter = AgenturenDateFormatter(
        timeFormatter = {
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault())
        },
        dateFormatter = {
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.getDefault())
        }
    )

    @Test
    fun myTest() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalAgenturenDateFormatter provides agenturenDateFormatter,
            ) {
                CreateContent(
                    state = CreateViewState(
                        title = "title",
                        description = "description",
                    ),
                    updateTitle = {},
                    updateDescription = {},
                    updateTime = {},
                    updateDate = {},
                    updateType = {},
                    save = {}
                )
            }
        }

        composeTestRule.onNodeWithText("title").assertIsDisplayed()
        composeTestRule.onNodeWithText("description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Daily").assertIsDisplayed()
        composeTestRule.onNodeWithText("Weekly").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create").assertIsDisplayed()
    }
}