package com.evg.test_essay.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.resource.R
import com.evg.test_essay.domain.model.EssayTestData
import com.evg.test_essay.presentation.model.CharactersNumberState
import com.evg.test_essay.presentation.mvi.TestEssayAction
import com.evg.test_essay.presentation.mvi.TestEssayState
import com.evg.ui.custom.RoundedButton
import com.evg.ui.snackbar.SnackBarController
import com.evg.ui.snackbar.SnackBarEvent
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.VerticalPadding
import com.evg.utils.model.TestLevelColors
import com.evg.utils.model.TestScore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun TestEssayScreen(
    state: TestEssayState,
    dispatch: (action: TestEssayAction) -> Unit,
    modifier: Modifier = Modifier,
    scoreAI: Int?,
    scoreHuman: Int?,
    isEditable: Boolean,
) {
    val scope = rememberCoroutineScope()
    val testData = state.testData
    var essayText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("qweqwe"))
    }
    var passedTime by rememberSaveable { mutableIntStateOf(testData?.passedTime ?: 0) }

    LaunchedEffect(Unit) {
        while (isEditable) {
            delay(1000)
            passedTime++
        }
    }

    LaunchedEffect(testData) {
        if (!isEditable && testData != null) {
            essayText = TextFieldValue(testData.essay)
            passedTime = testData.passedTime
        }
    }


    val charactersCount = essayText.text.length
    val charactersState = when {
        charactersCount <= 250 -> CharactersNumberState.NOT_ENOUGH
        charactersCount in 251..500 -> CharactersNumberState.NORMAL
        charactersCount in 501..1000 -> CharactersNumberState.ENOUGH
        else -> CharactersNumberState.MAXIMUM
    }

    val maximumCharactersExceeded = stringResource(R.string.maximum_characters_exceeded)
    val insufficientCharacters = stringResource(R.string.insufficient_characters)


    Column(
        modifier = modifier
            .padding(
                top = VerticalPadding,
                start = HorizontalPadding,
                end = HorizontalPadding,
            )
            .imePadding(),
    ) {
        Text(
            text = stringResource(id = R.string.essay_test_description),
            color = AppTheme.colors.textFieldPlaceholder,
            style = AppTheme.typography.body,
        )

        Spacer(modifier = Modifier.height(VerticalPadding))

        TextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors().copy(
                cursorColor = AppTheme.colors.primary,
                focusedContainerColor = AppTheme.colors.tileBackground,
                unfocusedContainerColor = AppTheme.colors.tileBackground,
                errorContainerColor = AppTheme.colors.tileBackground,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledContainerColor = AppTheme.colors.tileBackground,
                disabledIndicatorColor = Color.Transparent,
            ),
            enabled = !state.isTestSending && isEditable,
            shape = (RoundedCornerShape(BorderRadius)),
            textStyle = AppTheme.typography.body.copy(
                color = AppTheme.colors.text,
            ),
            supportingText = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        color = AppTheme.colors.textFieldPlaceholder,
                        text = "${essayText.text.length} ${stringResource(id = R.string.characters)}",
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    TimerDisplay(
                        timeInSeconds = passedTime,
                    )
                }

            },
            value = essayText,
            onValueChange = {
                essayText = it
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = VerticalPadding,
                    bottom = HorizontalPadding
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CharactersNumberVisualization(
                state = charactersState
            )

            Spacer(modifier = Modifier.weight(1f))

            if (isEditable) {
                RoundedButton(
                    modifier = Modifier,
                    backgroundColor = AppTheme.colors.secondary,
                    icon = painterResource(id = R.drawable.send),
                    iconColor = AppTheme.colors.text,
                    isLoading = state.isTestSending,
                    onClick = {
                        when (charactersState) {
                            CharactersNumberState.NOT_ENOUGH -> {
                                scope.launch { SnackBarController.sendEvent(event = SnackBarEvent(message = insufficientCharacters)) }
                            }
                            CharactersNumberState.MAXIMUM -> {
                                scope.launch { SnackBarController.sendEvent(event = SnackBarEvent(message = maximumCharactersExceeded)) }
                            }
                            else -> {
                                dispatch(
                                    TestEssayAction.SendTest(
                                        data = EssayTestData(essay = essayText.text, passedTime = passedTime)
                                    )
                                )
                            }
                        }
                    },
                )
            } else {
                val humanLevel = scoreHuman?.let { TestScore(scoreInit = it).level }
                val aiLevel = scoreAI?.let { TestScore(scoreInit = it).level } ?: TestLevelColors.UNKNOWN

                if (humanLevel != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = humanLevel.name,
                            style = AppTheme.typography.heading,
                            color = humanLevel.color,
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = aiLevel.name,
                            style = AppTheme.typography.body,
                            color = aiLevel.color.copy(alpha = 0.7f),
                            modifier = Modifier
                                .alpha(0.7f)
                                .graphicsLayer {
                                    clip = false
                                },
                            textDecoration = TextDecoration.LineThrough,
                        )
                    }
                } else {
                    Text(
                        text = aiLevel.name,
                        style = AppTheme.typography.heading,
                        color = aiLevel.color,
                    )
                }
            }
        }
    }
}
@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TestsListScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            TestEssayScreen(
                state = TestEssayState(
                    isTestSending = false,
                ),
                dispatch = {},
                scoreAI = 20,
                scoreHuman = 60,
                isEditable = false,
            )
        }
    }
}