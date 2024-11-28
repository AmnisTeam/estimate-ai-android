package com.evg.test_essay.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.resource.R
import com.evg.test_essay.presentation.model.CharactersNumberState
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme

@Composable
fun CharactersNumberVisualization(
    state: CharactersNumberState,
) {
    val stateText = when(state) {
        CharactersNumberState.NOT_ENOUGH -> stringResource(R.string.not_enough)
        CharactersNumberState.NORMAL -> stringResource(R.string.normal)
        CharactersNumberState.ENOUGH -> stringResource(R.string.enough)
        CharactersNumberState.MAXIMUM -> stringResource(R.string.maximum)
    }
    val filledBarsCount = when (state) {
        CharactersNumberState.NOT_ENOUGH -> 1
        CharactersNumberState.NORMAL -> 2
        CharactersNumberState.ENOUGH, CharactersNumberState.MAXIMUM -> 3
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = stringResource(R.string.number_characters),
            style = AppTheme.typography.body,
            color = AppTheme.colors.text,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .width(55.dp)
                        .height(7.dp)
                        .clip(shape = RoundedCornerShape(BorderRadius))
                        .background(
                            if (index < filledBarsCount) state.color
                            else Color(0xFF232332)
                        )
                )
            }
        }

        Text(
            text = stateText,
            style = AppTheme.typography.small,
            color = state.color,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CharactersNumberVisualizationPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            CharactersNumberVisualization(
                state = CharactersNumberState.NORMAL,
            )
        }
    }
}