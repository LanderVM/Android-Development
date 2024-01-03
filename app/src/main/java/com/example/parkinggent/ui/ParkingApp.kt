package com.example.parkinggent.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.parkinggent.R
import com.example.parkinggent.ui.screens.detailscreen.DetailScreen
import com.example.parkinggent.ui.screens.homescreen.ParkingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navController.navigateUp() }
    val currentScreenTitle = backStackEntry?.destination?.route
        ?.let { route ->
            NavigationRoutes.values().firstOrNull { route.startsWith(it.name) }
        }?.title ?: NavigationRoutes.HOME.title

    Scaffold(
        topBar = {
            AppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp,
                currentScreenTitle = currentScreenTitle,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.HOME.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = NavigationRoutes.HOME.name) {
                ParkingScreen { parkingId ->
                    navController.navigate("${NavigationRoutes.ABOUT.name}/$parkingId")
                }
            }
            composable(
                route = "${NavigationRoutes.ABOUT.name}/{parkingId}",
                arguments = listOf(navArgument("parkingId") { type = NavType.StringType })
            ) { backStackEntry ->
                val parkingId =
                    backStackEntry.arguments?.getString("parkingId") ?: return@composable
                DetailScreen(parkingId = parkingId)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    currentScreenTitle: Int
) {
    TopAppBar(
        modifier = modifier.shadow(6.dp),
        colors = TopAppBarDefaults.smallTopAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(stringResource(id = currentScreenTitle))
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.appBarr_NavigateBackDescription),
                    )
                }
            }
        },
    )
}

