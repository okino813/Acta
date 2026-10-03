package com.suscorp.acta

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import androidx.navigation.compose.rememberNavController
import com.suscorp.acta.Views.Components.BottomNavigationBar
import com.suscorp.acta.Views.Components.Navigation
import com.suscorp.acta.ui.theme.ActaTheme
import android.util.Log
import androidx.compose.ui.res.stringResource

class MainActivity : AppCompatActivity() {

    object LanguageManager {
        public final val languages = listOf(
            AppLanguage("fr", "Français"),
            AppLanguage("en", "English"),
        )
        fun setLanguage(
            code: String
        ) {
            val locale = LocaleListCompat.forLanguageTags(code)
            AppCompatDelegate.setApplicationLocales(locale)
        }

        fun getLanguage(): String {
            val locale = getLabelByCode(
                AppCompatDelegate.getApplicationLocales()[0].toString()
            )
            return locale
        }

        fun getCodeByLabel(label: String): String {
            return languages.find { it.label == label }?.code ?: "en"
        }

        fun getLabelByCode(code: String): String {
            return languages.find { it.code == code }?.label ?: "English"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPreferences = getSharedPreferences("ActaPreferences",Context.MODE_PRIVATE)

        setContent {

            val themeList = listOf(
                AppTheme("none", stringResource(id= R.string.none_appearance)),
                AppTheme("dark", stringResource(id= R.string.dark_appearance)),
                AppTheme("light",stringResource(id= R.string.light_appearance)),
            )

            fun getLabelByCode(
                code: String
            ): String {
                return themeList.find { it.code == code }?.label ?: "none"
            }
            // Check if theme is define in SharedPreference
            var themeChoice by remember { mutableStateOf(sharedPreferences.getString("choiceTheme", "none") ?: "none" ) }
            var themeChoiceText by remember { mutableStateOf(getLabelByCode(themeChoice) ?: "Undefined") }

            var language by remember { mutableStateOf(LanguageManager.getLanguage()) }
            val languageList = LanguageManager.languages

            fun changeThemeMode(
                mode: String
            ){
                themeChoice = mode
                themeChoiceText = getLabelByCode(mode)
                sharedPreferences.edit()
                    .putString("choiceTheme", mode)
                    .apply()
            }

            ActaTheme (
                themeChoice = themeChoice,
                context = this,
                content = {
                    MainComposable(
                        modifier = Modifier.fillMaxSize(),
                        context = this,
                        changeThemeMode = ::changeThemeMode,
                        onChangeLanguage = { code -> LanguageManager.setLanguage(code) },
                        currentLanguage = language,
                        languageList = languageList,
                        themeList = themeList,
                        currentTheme = themeChoiceText
                    )
                }
            )
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainComposable(
    modifier: Modifier = Modifier,
    context : Context,
    changeThemeMode: (String) -> Unit,
    onChangeLanguage: (String) -> Unit,
    currentLanguage: String,
    languageList: List<AppLanguage>,
    themeList: List<AppTheme>,
    currentTheme: String
){
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        modifier = modifier,
        content = {
            Box() {
                Navigation(
                    navController = navController,
                    changeThemeMode = changeThemeMode,
                    onChangeLanguage = onChangeLanguage,
                    currentLanguage = currentLanguage,
                    languageList = languageList,
                    themeList = themeList,
                    currentTheme = currentTheme
                )
            }
        }

    )
}