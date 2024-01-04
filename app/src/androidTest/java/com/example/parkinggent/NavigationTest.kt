package com.example.parkinggent


import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.parkinggent.ui.NavigationRoutes
import com.example.parkinggent.ui.ParkingApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ParkingNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private var shortDelay = 300L
    private var longDelay = 1500L

    @Before
    fun setupParkingGentAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            ParkingApp(navController = navController)
        }
    }

    @Test
    fun parkingGentNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(NavigationRoutes.HOME.name)
    }

    @Test
    fun parkingHomeScreen_hasSortButtons() {
        navController.assertCurrentRouteName(NavigationRoutes.HOME.name)

        composeTestRule.onNodeWithStringText(R.string.parkingScreen_SortName)
            .assertExists()
        composeTestRule.onNodeWithStringText(R.string.parkingScreen_SortFreeSpaces)
            .assertExists()
        composeTestRule.onNodeWithStringText(R.string.parkingScreen_Sort)
            .assertDoesNotExist()
    }

    @Test
    fun parkingGentNavHost_clickCard_navigateToDetailsScreen() {
        openFistParkingCard()
    }

    @Test
    fun parkingGentNavHost_goToDetails_canNavigateBack() {
        openFistParkingCard()

        runBlocking {
            delay(shortDelay)

            composeTestRule.onNodeWithStringContentDescription(R.string.appBarr_NavigateBackDescription)
                .assertExists()
                .performClick()

            navController.assertCurrentRouteName(NavigationRoutes.HOME.name)
        }
    }

    private fun openFistParkingCard() {
        runBlocking {
            delay(longDelay)
            composeTestRule.onAllNodesWithStringTag(R.string.navigationTest_parkingCardTag)
                .onFirst()
                .assertExists()
                .performClick()

            navController.assertCurrentRouteName("${NavigationRoutes.ABOUT.name}/{parkingId}")
        }
    }
}