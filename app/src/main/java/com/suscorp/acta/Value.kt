package com.suscorp.acta

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
sealed class NavigationItem(
    val route: String,
    @StringRes val titleRes: Int,
    @DrawableRes val icon: Int
) {
    object Home : NavigationItem(
        route = "home",
        titleRes = R.string.home_title,
        icon = R.drawable.home_light
    )

    object Gallery : NavigationItem(
        route = "gallery",
        titleRes = R.string.gallery_title,
        icon = R.drawable.gallery_light
    )

    object Settings : NavigationItem(
        route = "settings",
        titleRes = R.string.settings_title,
        icon = R.drawable.settings_light
    )
}

data class AppLanguage(
    val code: String,
    val label: String
)

data class AppTheme(
    val code: String,
    val label: String
)

