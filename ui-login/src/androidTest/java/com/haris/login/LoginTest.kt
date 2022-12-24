package com.haris.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class LoginTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            LoginContent(state = LoginViewState(
                email = "haris@gmail.com",
                password = "password",
                isLoading = false,
                isButtonEnabled = false
            ), updateEmail = {}, updatePassword = {}, login = {}, skipLogin = {})
        }

        composeTestRule.onNodeWithText("Enter email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Enter password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Skip login").assertIsDisplayed()
    }
}