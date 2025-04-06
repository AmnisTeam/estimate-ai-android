package com.evg.test_essay.presentation

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme

@Composable
fun TimerDisplay(
    timeInSeconds: Int,
) {
    val minutes = timeInSeconds / 60
    val seconds = timeInSeconds % 60
    val formattedTime = "%02d:%02d".format(minutes, seconds)

    Text(
        text = formattedTime,
        color = AppTheme.colors.text,
        style = AppTheme.typography.small,
    )
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TimerDisplayPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            TimerDisplay(
                timeInSeconds = 100,
            )
        }
    }
}