package com.evg.tests_list.presentation

import android.content.res.Configuration
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
import androidx.compose.ui.unit.dp
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

@Composable
fun TestsListScreen(
    state: TestsListState,
    modifier: Modifier = Modifier,
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        RoundedButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(ButtonPadding),
            backgroundColor = if (isSystemInDarkTheme()) darkAddButtonColor else lightAddButtonColor,
            icon = painterResource(id = R.drawable.plus),
            iconColor = AppTheme.colors.primary,
            onClick = {
                onTestSelectScreen()
            },
        )
    }

}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TestsListScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            TestsListScreen(
                state = TestsListState(
                    isTestsLoading = false,
                ),
                onTestSelectScreen = {},
                onTestEssayScreen = {},
                getAllTests = {},
            )
        }
    }
}