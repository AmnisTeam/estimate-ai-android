package com.evg.tests_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
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
import androidx.compose.ui.unit.dp

@Composable
fun TestsListScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    state: TestsListState,
    getAllTests: () -> Unit,
) {
    val tests = state.tests.collectAsLazyPagingItems()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = HorizontalPaddingTile,
                vertical = VerticalPadding,
            ),
    ) {
        TestsLazyColumn(
            navigation = navigation,
            tests = tests,
            getAllTests = getAllTests,
            isTestsLoading = state.isTestsLoading,
        )
        RoundedButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp),
            backgroundColor = if (isSystemInDarkTheme()) darkAddButtonColor else lightAddButtonColor,
            icon = painterResource(id = R.drawable.plus),
            iconColor = AppTheme.colors.primary,
            onClick = {
                navigation.navigate("test-select")
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
                navigation = NavHostController(LocalContext.current),
                state = TestsListState(
                    isTestsLoading = false,
                ),
                getAllTests = {},
            )
        }
    }
}