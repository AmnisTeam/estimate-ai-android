package com.evg.account.presentation

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.evg.account.domain.mapper.toAppCompatDelegateTheme
import com.evg.account.domain.mapper.toResourceAppStyle
import com.evg.account.domain.model.AppLanguage
import com.evg.account.domain.model.TestingLanguage
import com.evg.account.presentation.mvi.AccountAction
import com.evg.account.presentation.mvi.AccountState
import com.evg.account.presentation.settingstile.StyleTile
import com.evg.account.presentation.settingstile.Tile
import com.evg.account.presentation.settingstile.TileBlock
import com.evg.resource.R
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
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
    val context = LocalContext.current
    
    Column(
        modifier = modifier
            .padding(
                horizontal = HorizontalPadding,
                vertical = VerticalPadding,
            )
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(BorderRadius))
                .fillMaxWidth()
                .background(AppTheme.colors.textFieldBackground)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(60.dp)
                    .padding(5.dp),
                painter = painterResource(R.drawable.user_circle),
                tint = AppTheme.colors.text,
                contentDescription = null,
            )
            Spacer(Modifier.width(10.dp))
            Text(
                style = AppTheme.typography.body,
                text = state.user ?: stringResource(R.string.no_username),
                color = AppTheme.colors.text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
        
        Spacer(Modifier.height(5.dp))

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
                            AppTheme.nightMode = it.toAppCompatDelegateTheme()
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

        Spacer(Modifier.weight(1f))

        Button(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth()
                .height(40.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = AppTheme.colors.primary,
            ),
            shape = RoundedCornerShape(BorderRadius),
            onClick = {
                dispatch(AccountAction.Logout)
                onLoginScreen()
            }
        ) {
            Text(
                color = AppTheme.colors.background,
                style = AppTheme.typography.body,
                text = stringResource(R.string.logout)
            )
        }
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
                    user = "name.surname",
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
