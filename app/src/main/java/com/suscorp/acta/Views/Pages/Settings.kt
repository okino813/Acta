package com.suscorp.acta.Views.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.suscorp.acta.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.suscorp.acta.AppLanguage
import com.suscorp.acta.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelector(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    languages: List<AppLanguage>
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer).fillMaxWidth().padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Translate,
                contentDescription = stringResource(R.string.language_appearance),
                modifier = Modifier.size(40.dp).padding(end = 10.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = selectedLanguage,
                    onValueChange = {}, // Read-only, no manual input
                    readOnly = true,
                    label = { Text(stringResource(R.string.language_appearance)) },
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    languages.forEach { lang ->
                        DropdownMenuItem(
                            text = { Text(lang.label) },
                            onClick = {
                                onLanguageSelected(lang.code)
                                expanded = false
                            },
                            leadingIcon = {
                                if (selectedLanguage == lang.label) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = Color(0xFF6d4aff)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelector(
    selectedTheme: String,
    onThemeSelected: (String) -> Unit,
    themes: List<AppTheme>
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer).fillMaxWidth().padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.InvertColors,
                contentDescription = stringResource(R.string.theme_appearance) ,
                modifier = Modifier.size(40.dp).padding(end = 10.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = selectedTheme,
                    onValueChange = {}, // Read-only, no manual input
                    readOnly = true,
                    label = { Text(stringResource(R.string.theme_appearance)) },
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    themes.forEach { theme ->
                        DropdownMenuItem(
                            text = { Text(theme.label) },
                            onClick = {
                                onThemeSelected(theme.code)
                                expanded = false
                            },
                            leadingIcon = {
                                if (selectedTheme == theme.code) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = Color(0xFF6d4aff)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    changeThemeMode:(String) -> Unit,
    onChangeLanguage: (String) -> Unit,
    currentLanguage: String,
    languageList: List<AppLanguage>,
    currentTheme: String,
    themeList: List<AppTheme>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.settings_title),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.subtitle_appearance),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(5.dp))

        ThemeSelector(
            selectedTheme = currentTheme,
            onThemeSelected = changeThemeMode,
            themes = themeList
        )

       LanguageSelector(
           selectedLanguage = currentLanguage,
           onLanguageSelected = onChangeLanguage,
           languages = languageList
       )
    }
}