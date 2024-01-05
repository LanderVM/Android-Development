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

/**
 * A test class for the navigation aspects of the Parking Gent app.
 * It uses the Jetpack Compose testing framework to simulate navigation in a test environment.
 */
class ParkingNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private var shortDelay = 300L
    private var longDelay = 1500L

    /**
     * Sets up the navigation environment for testing the Parking Gent app.
     * This is executed before each test.
     */
    @Before
    fun setupParkingGentAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            ParkingApp(navController = navController)
        }
    }

    /**
     * Verifies that the start destination of the navigation is correct.
     */
    @Test
    fun parkingGentNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(NavigationRoutes.HOME.name)
    }

    /**
     * Checks if the parking home screen contains sort buttons.
     */
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

    /**
     * Tests navigation to the details screen when a parking card is clicked.
     */
    @Test
    fun parkingGentNavHost_clickCard_navigateToDetailsScreen() {
        openFistParkingCard()
    }

    /**
     * Tests if the app can navigate back to the home screen from the details screen.
     */
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

    /**
     * Simulates a user click on the first parking card and verifies navigation to the details screen.
     */
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