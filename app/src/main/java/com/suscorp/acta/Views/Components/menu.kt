package com.suscorp.acta.Views.Components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.suscorp.acta.AppLanguage
import com.suscorp.acta.AppTheme
import com.suscorp.acta.NavigationItem
import com.suscorp.acta.Views.Pages.GalleryScreen
import com.suscorp.acta.Views.Pages.HomeScreen
import com.suscorp.acta.Views.Pages.SettingsScreen
import com.suscorp.acta.ui.theme.Blue1

@Composable
fun Navigation(
    navController: NavHostController,
    changeThemeMode : (String) -> Unit,
    onChangeLanguage : (String) -> Unit,
    currentLanguage: String,
    languageList: List<AppLanguage>,
    currentTheme: String,
    themeList: List<AppTheme>
) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Gallery.route) {
            GalleryScreen()
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen(
                changeThemeMode,
                onChangeLanguage,
                currentLanguage,
                languageList,
                currentTheme,
                themeList
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Gallery,
        NavigationItem.Settings
    )

    NavigationBar(
        containerColor = Blue1
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(

                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = stringResource(item.titleRes),
                        tint = Color.White,
                        modifier = if(currentRoute == item.route) Modifier.size(30.dp) else Modifier.size(20.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Blue1
                ),
                label = { Text(text = stringResource(item.titleRes), color= Color.White) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
