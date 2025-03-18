package com.evg.account.presentation.settingstile

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.account.domain.model.AppStyle
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.palettes.blueDarkPalette
import com.evg.ui.theme.palettes.blueLightPalette
import com.evg.ui.theme.palettes.greenDarkPalette
import com.evg.ui.theme.palettes.greenLightPalette
import com.evg.ui.theme.palettes.purpleDarkPalette
import com.evg.ui.theme.palettes.purpleLightPalette

@Composable
fun StyleTile(
    onStyleSelected: (AppStyle) -> Unit,
) {
    val isDarkMode = isSystemInDarkTheme()

    val styleColors = remember {
        if (isDarkMode) {
            listOf(
                purpleDarkPalette.primary to AppStyle.PURPLE,
                greenDarkPalette.primary to AppStyle.GREEN,
                blueDarkPalette.primary to AppStyle.BLUE,
            )
        } else {
            listOf(
                purpleLightPalette.primary to AppStyle.PURPLE,
                greenLightPalette.primary to AppStyle.GREEN,
                blueLightPalette.primary to AppStyle.BLUE,
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            styleColors.subList(0, 2).forEach { (color, style) ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(BorderRadius))
                        .background(color)
                        .clickableRipple {
                            onStyleSelected(style)
                        }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            styleColors.subList(2, 3).forEach { (color, style) ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(BorderRadius))
                        .background(color)
                        .clickableRipple {
                            onStyleSelected(style)
                        }
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun StyleTilePreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            StyleTile(
                onStyleSelected = {}
            )
        }
    }
}
