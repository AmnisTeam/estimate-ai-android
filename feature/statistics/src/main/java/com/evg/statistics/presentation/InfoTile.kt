package com.evg.statistics.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import androidx.compose.ui.unit.dp

@Composable
fun InfoTile(
    title: String,
    info: String,
) {
    Box(
        modifier = Modifier
            .height(100.dp)
            //.widthIn(min = 100.dp, max = 150.dp)
            .width(140.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AppTheme.colors.primary)
    ) {
        val startPadding = 1.dp
        val endPadding = 4.dp
        Box(
            modifier = Modifier
                .padding(
                    top = startPadding,
                    start = startPadding,
                    bottom = endPadding,
                    end = endPadding,
                )
                .clip(RoundedCornerShape(10.dp))
                .background(AppTheme.colors.textFieldBackground)
                .padding(20.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = title,
                        color = AppTheme.colors.text,
                        style = AppTheme.typography.small,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = info,
                    color = AppTheme.colors.text,
                    style = AppTheme.typography.body,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun InfoTilePreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            InfoTile(
                title = "Frequent day",
                info = "Friday",
            )
        }
    }
}