package com.example.parkinggent

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import junit.framework.TestCase.assertEquals

/**
 * Asserts that the current route in the NavController matches the expected route.
 *
 * @param expectedRouteName The expected name of the current route.
 */
fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}

/**
 * Finds a node with text from a string resource.
 *
 * @param id The resource ID of the string.
 * @return A SemanticsNodeInteraction representing the node found.
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringText(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))

/**
 * Finds a node with content description from a string resource.
 *
 * @param id The resource ID of the content description.
 * @return A SemanticsNodeInteraction representing the node found.
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringContentDescription(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithContentDescription(activity.getString(id))

/**
 * Finds all nodes with a tag from a string resource.
 *
 * @param id The resource ID of the tag.
 * @return A SemanticsNodeInteractionCollection representing all nodes found.
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onAllNodesWithStringTag(
    @StringRes id: Int
): SemanticsNodeInteractionCollection = onAllNodesWithTag(activity.getString(id))
