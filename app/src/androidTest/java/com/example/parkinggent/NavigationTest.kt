package com.example.parkinggent


import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.parkinggent.data.ParkingSampler
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

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.parkingScreen_SortName))
            .assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.parkingScreen_SortFreeSpaces))
            .assertExists()
        composeTestRule.onNodeWithText("Sort")
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
            delay(300L)

            composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.appBarr_NavigateBackDescription))
           .assertExists()
            .performClick()

        navController.assertCurrentRouteName(NavigationRoutes.HOME.name)
        }
    }

    private  fun openFistParkingCard(){
        runBlocking {
            delay(1500L)
            composeTestRule.onAllNodesWithTag("parkingCardTag")
                .onFirst()
                .performClick()

            navController.assertCurrentRouteName("${NavigationRoutes.ABOUT.name}/{parkingId}")
        }
    }
}