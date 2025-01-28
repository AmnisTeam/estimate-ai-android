package com.evg.test_select.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TestSelectRoot(
    modifier: Modifier,
    onTestEssayScreen: () -> Unit,
) {

    TestSelectScreen(
        modifier = modifier,
        onTestEssayScreen = onTestEssayScreen,
    )
}