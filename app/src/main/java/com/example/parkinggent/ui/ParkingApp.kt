package com.example.parkinggent.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.parkinggent.R
import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.ui.screens.detailscreen.DetailScreen
import com.example.parkinggent.ui.screens.homescreen.ParkingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingApp(navController: NavHostController = rememberNavController()){
    val backStackEntry by navController.currentBackStackEntryAsState()

    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navController.navigateUp() }
    val currentScreenTitle = NavigationRoutes.valueOf(
        backStackEntry?.destination?.route ?: NavigationRoutes.HOME.name,
    ).title

    Scaffold(
        topBar = {
            TaskAppAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp,
                currentScreenTitle = currentScreenTitle,
            )
        }
    ){
            innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.HOME.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = NavigationRoutes.HOME.name) {
                ParkingScreen(navigateToAbout = {navController.navigate(NavigationRoutes.ABOUT.name)})
            }
            composable(route = NavigationRoutes.ABOUT.name) {
                Text(text = "About")
            //DetailScreen(ParkingInfo())
                //get current or selected parking
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAppAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    currentScreenTitle: Int,
    modifier: Modifier = Modifier
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
                        contentDescription = "navigate back",
                    )
                }
            }
        },
    )
}

