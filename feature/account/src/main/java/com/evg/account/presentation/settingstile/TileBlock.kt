package com.evg.account.presentation.settingstile

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.evg.account.domain.model.AppLanguage
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme

@Composable
fun ColumnScope.TileBlock(
    tileContents: List<@Composable () -> Unit>,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(BorderRadius))
            .background(AppTheme.colors.tileBackground)
            .fillMaxWidth()
    ) {
        Column {
            tileContents.forEachIndexed { index, tileContent ->
                tileContent()
                if (index != tileContents.lastIndex) {
                    HorizontalDivider(
                        color = AppTheme.colors.secondary,
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TileBlockPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            Column {
                TileBlock(
                    tileContents = listOf(
                        {
                            Tile(
                                title = "App language",
                                initialValue = AppLanguage.ENGLISH,
                                options = AppLanguage.entries,
                                onOptionSelected = {},
                                optionStringRes = { it.labelRes },
                            )
                        },
                        {
                            Tile(
                                title = "App theme",
                                initialValue = com.evg.account.domain.model.AppTheme.USER,
                                options = com.evg.account.domain.model.AppTheme.entries,
                                onOptionSelected = {},
                                optionStringRes = { it.labelRes },
                            )
                        }
                    )
                )
            }
        }
    }
}