package com.evg.test_essay.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.evg.resource.R
import com.evg.test_essay.presentation.mvi.TestEssayState
import com.evg.ui.custom.Header
import com.evg.ui.custom.RoundedButton
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.ButtonPadding
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.VerticalPadding

@Composable
fun TestEssayScreen(
    navigation: NavHostController,
    state: TestEssayState,
) {
    var essayText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("qweqwe"))
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Header(
                navigation = navigation,
                title = stringResource(id = R.string.essay_test)
            )

            Spacer(modifier = Modifier.height(VerticalPadding))

            Column(
                modifier = Modifier
                    .padding(horizontal = HorizontalPadding)
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
                    ),
                    enabled = !state.isTestSendingLoading,
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
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        RoundedButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(ButtonPadding),
            backgroundColor = AppTheme.colors.secondary,
            icon = painterResource(id = R.drawable.send),
            iconColor = AppTheme.colors.text,
            onClick = {
                navigation.navigate("tests")
            },
        )
    }

}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TestsListScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            TestEssayScreen(
                navigation = NavHostController(LocalContext.current),
                state = TestEssayState(
                    isTestSendingLoading = false,
                ),
            )
        }
    }
}