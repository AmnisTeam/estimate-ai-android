package com.evg.account.presentation

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.LocaleListCompat
import com.evg.resource.R
import com.evg.account.domain.mapper.toIsDarkTheme
import com.evg.account.domain.mapper.toResourceAppStyle
import com.evg.account.domain.model.AppLanguage
import com.evg.account.domain.model.TestingLanguage
import com.evg.account.presentation.mvi.AccountAction
import com.evg.account.presentation.mvi.AccountState
import com.evg.account.presentation.settingstile.StyleTile
import com.evg.account.presentation.settingstile.TileBlock
import com.evg.account.presentation.settingstile.Tile
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.VerticalPadding

@Composable
fun AccountScreen(
    state: AccountState,
    dispatch: (action: AccountAction) -> Unit,
    modifier: Modifier = Modifier,
    onLoginScreen: () -> Unit,
) {
    val isDarkTheme = isSystemInDarkTheme()
    val context = LocalContext.current
    
    Column(
        modifier = modifier
            .padding(
                horizontal = HorizontalPadding,
                vertical = VerticalPadding,
            )
            .verticalScroll(rememberScrollState()),
    ) {
        TileBlock(
            tileContents = listOf(
                {
                    Tile(
                        title = stringResource(R.string.app_language),
                        initialValue = state.appLanguage,
                        options = AppLanguage.entries,
                        optionStringRes = { it.labelRes },
                        onOptionSelected = {
                            dispatch(AccountAction.SaveAppLanguage(language = it))
                            setLanguage(context = context, code = it.code)
                        },
                    )
                },
                {
                    Tile(
                        title = stringResource(R.string.app_theme),
                        initialValue = state.appTheme,
                        options = com.evg.account.domain.model.AppTheme.entries,
                        optionStringRes = { it.labelRes },
                        onOptionSelected = {
                            dispatch(AccountAction.SaveAppTheme(theme = it))
                            AppTheme.themeIsDark = it.toIsDarkTheme(isDarkTheme = isDarkTheme)
                        },
                    )
                },
                {
                    Tile(
                        title = stringResource(R.string.testing_language),
                        initialValue = state.testingLanguage,
                        options = TestingLanguage.entries,
                        optionStringRes = { it.labelRes },
                        onOptionSelected = {
                            dispatch(AccountAction.SaveTestingLanguage(language = it))
                        },
                    )
                },
                {
                    StyleTile(
                        onStyleSelected = {
                            dispatch(AccountAction.SaveAppStyle(style = it))
                            AppTheme.style = it.toResourceAppStyle()
                        }
                    )
                }
            )
        )
    }
}

fun setLanguage(context: Context, code: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(code)
    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
    }
}

@Composable
@Preview(showBackground = true)
fun AccountScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            AccountScreen(
                state = AccountState(
                    appTheme = com.evg.account.domain.model.AppTheme.USER,
                    appLanguage = AppLanguage.RUSSIAN,
                    testingLanguage = TestingLanguage.ENGLISH,
                ),
                dispatch = {},
                onLoginScreen = {},
            )
        }
    }
}
