package com.evg.test_select.presentation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TestSelectRoot(
    modifier: Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onTestEssayScreen: () -> Unit,
) {

    TestSelectScreen(
        modifier = modifier,
        animatedVisibilityScope = animatedVisibilityScope,
        onTestEssayScreen = onTestEssayScreen,
    )
}