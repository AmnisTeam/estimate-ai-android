package com.evg.tests_list.presentation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import com.evg.resource.R
import com.evg.tests_list.presentation.mvi.TestsListState
import com.evg.ui.custom.RoundedButton
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.ButtonPadding
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPaddingTile
import com.evg.ui.theme.VerticalPadding
import com.evg.ui.theme.darkAddButtonColor
import com.evg.ui.theme.lightAddButtonColor

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TestsListScreen(
    state: TestsListState,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onTestSelectScreen: () -> Unit,
    onTestEssayScreen: (Int) -> Unit,
    getAllTests: () -> Unit,
) {
    val tests = state.tests.collectAsLazyPagingItems()

    Box(
        modifier = modifier
            .padding(
                horizontal = HorizontalPaddingTile,
                vertical = VerticalPadding,
            ),
    ) {
        TestsLazyColumn(
            onTestEssayScreen = onTestEssayScreen,
            tests = tests,
            getAllTests = getAllTests,
            isTestsLoading = state.isTestsLoading,
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        RoundedButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(ButtonPadding)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = "FAB_EXPLODE_BOUNDS_KEY"
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                ),
            backgroundColor = if (isSystemInDarkTheme()) darkAddButtonColor else lightAddButtonColor,
            icon = painterResource(id = R.drawable.plus),
            iconColor = AppTheme.colors.primary,
            onClick = {
                onTestSelectScreen()
            },
        )
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TestsListScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            SharedTransitionLayout {
                AnimatedVisibility(visibleState = MutableTransitionState(true)) {
                    TestsListScreen(
                        state = TestsListState(
                            isTestsLoading = false,
                        ),
                        animatedVisibilityScope = this,
                        onTestSelectScreen = {},
                        onTestEssayScreen = {},
                        getAllTests = {},
                    )
                }
            }
        }
    }
}