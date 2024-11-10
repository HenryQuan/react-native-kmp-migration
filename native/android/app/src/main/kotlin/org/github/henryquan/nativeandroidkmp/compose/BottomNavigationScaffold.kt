package org.github.henryquan.nativeandroidkmp.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

data class BottomNavigationItem(
    val route: BottomNavigationRoutes,
    val icon: ImageVector,
)

enum class BottomNavigationRoutes {
    Compose, ReactNative, Flutter, All;

    fun item(): BottomNavigationItem {
        return when (this) {
            Compose -> BottomNavigationItem(Compose, Icons.Default.Home)
            ReactNative -> BottomNavigationItem(ReactNative, Icons.Default.Home)
            Flutter -> BottomNavigationItem(Flutter, Icons.Default.Home)
            All -> BottomNavigationItem(All, Icons.Default.Home)
        }
    }

    @Composable
    fun displayName(): String {
        // for localisation, you can call
        // stringResource(id = R.string.compose)
        return when (this) {
            Compose -> "Compose"
            ReactNative -> "React Native"
            Flutter -> "Flutter"
            All -> "All"
        }
    }
}

@Composable
fun BottomNavigationScaffold(
    navController: NavHostController,
    startDestination: String,
) {
    Scaffold(bottomBar = {
        BottomNavigationBarView(navController)
    }) { innerPadding ->
        NavHostView(navController, startDestination, Modifier.padding(innerPadding))
    }
}

@Composable
fun BottomNavigationBarView(
    navController: NavHostController,
) {
    var selectedTabName by remember { mutableStateOf(BottomNavigationRoutes.Compose.name) }
    NavigationBar {
        BottomNavigationRoutes.entries.forEachIndexed { index, route ->
            val item = route.item()
            val destination = item.route.name
            NavigationBarItem(icon = {
                Icon(
                    imageVector = item.icon, contentDescription = null
                )
            }, label = {
                Text(item.route.displayName())
            }, selected = selectedTabName == destination, onClick = {
                navController.navigate(destination)
                selectedTabName = destination
            })
        }
    }
}

@Composable
fun NavHostView(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        // Compose screen
        composable(BottomNavigationRoutes.Compose.name) {

        }
        // React Native screen
        composable(BottomNavigationRoutes.ReactNative.name) {

        }
        // Flutter screen
        composable(BottomNavigationRoutes.Flutter.name) {

        }
        // All screens in one
        composable(BottomNavigationRoutes.All.name) {

        }
    }
}
