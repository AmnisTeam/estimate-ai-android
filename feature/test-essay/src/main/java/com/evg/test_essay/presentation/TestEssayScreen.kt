package com.evg.test_essay.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.resource.R
import com.evg.test_essay.domain.model.EssayTestData
import com.evg.test_essay.presentation.model.CharactersNumberState
import com.evg.test_essay.presentation.mvi.TestEssayState
import com.evg.ui.custom.RoundedButton
import com.evg.ui.snackbar.SnackBarController
import com.evg.ui.snackbar.SnackBarEvent
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.ButtonPadding
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.VerticalPadding
import kotlinx.coroutines.launch

@Composable
fun TestEssayScreen(
    state: TestEssayState,
    modifier: Modifier = Modifier,
    onBackScreen: () -> Unit,
    sendTest: (EssayTestData) -> Unit,
    isEditable: Boolean,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val textData: EssayTestData? = state.testData.collectAsState().value
    var essayText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("qweqwe"))
    }

    LaunchedEffect(textData) {
        if (!isEditable && textData != null) {
            essayText = TextFieldValue(textData.essay)
        }
        println(textData)
    }


    val charactersCount = essayText.text.length
    val charactersState = when {
        charactersCount <= 100 -> CharactersNumberState.NOT_ENOUGH
        charactersCount in 101..200 -> CharactersNumberState.NORMAL
        charactersCount in 201..400 -> CharactersNumberState.ENOUGH
        else -> CharactersNumberState.MAXIMUM
    }
    val horizontalPadding = 40.dp

    val maximumCharactersExceeded = stringResource(R.string.maximum_characters_exceeded)


    Column(
        modifier = modifier
            .padding(
                vertical = VerticalPadding,
                horizontal = HorizontalPadding,
            )
            .imePadding(),
    ) {
        Text(
            text = stringResource(id = R.string.essay_test_description),
            color = AppTheme.colors.textField,
            style = AppTheme.typography.body,
        )

        Spacer(modifier = Modifier.height(VerticalPadding))

        TextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors().copy(
                cursorColor = AppTheme.colors.primary,
                focusedContainerColor = AppTheme.colors.textFieldBackground,
                unfocusedContainerColor = AppTheme.colors.textFieldBackground,
                errorContainerColor = AppTheme.colors.textFieldBackground,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledContainerColor = AppTheme.colors.textFieldBackground,
                disabledIndicatorColor = Color.Transparent,
            ),
            enabled = !state.isTestSending && isEditable,
            shape = (RoundedCornerShape(BorderRadius)),
            textStyle = AppTheme.typography.body.copy(
                color = AppTheme.colors.text,
            ),
            supportingText = {
                Text(
                    color = AppTheme.colors.textField,
                    text = "${essayText.text.length} ${stringResource(id = R.string.characters)}",
                )
            },
            value = essayText,
            onValueChange = {
                essayText = it
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = VerticalPadding)
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
                        if (charactersState == CharactersNumberState.MAXIMUM) {
                            scope.launch {
                                SnackBarController.sendEvent(event = SnackBarEvent(message = maximumCharactersExceeded))
                            }
                        } else {
                            sendTest(
                                EssayTestData(
                                    essay = essayText.text,
                                )
                            )
                        }
                    },
                )
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
                onBackScreen = {},
                sendTest = {},
                isEditable = true,
            )
        }
    }
}