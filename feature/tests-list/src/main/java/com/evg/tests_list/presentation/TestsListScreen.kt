package com.evg.tests_list.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.evg.api.domain.utils.ServerResult
import com.evg.model.TestIcons
import com.evg.model.TestLevelColors
import com.evg.resource.R
import com.evg.tests_list.domain.model.FinishedTest
import com.evg.tests_list.domain.model.LoadingTest
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.presentation.model.TestState
import com.evg.tests_list.presentation.mvi.TestsListState
import com.evg.ui.custom.RoundedButton
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.ButtonPadding
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.HorizontalPaddingTile
import com.evg.ui.theme.VerticalPadding
import com.evg.ui.theme.darkAddButtonColor
import com.evg.ui.theme.lightAddButtonColor

@Composable
fun TestsListScreen(
    navigation: NavHostController,
    state: TestsListState,
) {
    val context = LocalContext.current
    val tests = state.tests.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = HorizontalPaddingTile,
                vertical = VerticalPadding,
            ),
    ) {
        when (tests.loadState.refresh) {
            is LoadState.Loading -> {

            }
            is LoadState.NotLoading -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        count = tests.itemCount,
                    ) { index ->
                        when (val item = tests[index]) {
                            is ServerResult.Success -> {
                                when (item.data) {
                                    is TestType.OnLoadingTestType -> {
                                        LoadingTestTile(
                                            loadingTest = TestState.LoadingTest(
                                                loadingTest = LoadingTest(
                                                    icon = TestIcons.ESSAY,
                                                    title = (item.data as TestType.OnLoadingTestType).id.toString(),
                                                    description = "Write an essay on any topic. Your English level will be estimated based on it.",
                                                    progress = (item.data as TestType.OnLoadingTestType).progress,
                                                ),
                                            ),
                                            onClick = {},
                                        )
                                    }
                                    is TestType.OnReadyTestType -> {
                                        FinishedTestTile(
                                            finishedTest = TestState.FinishedTest(
                                                finishedTest = FinishedTest(
                                                    icon = TestIcons.ESSAY,
                                                    title = (item.data as TestType.OnReadyTestType).id.toString(),
                                                    description = "Write an essay on any topic. Your English level will be estimated based on it.",
                                                    level = (item.data as TestType.OnReadyTestType).level,
                                                    levelColor = TestLevelColors.A2,
                                                ),
                                            ),
                                            onClick = {},
                                        )
                                    }
                                    is TestType.OnErrorTestType -> TODO()
                                }
                            }
                            is ServerResult.Error -> {
                                Toast.makeText(context, item.error.name, Toast.LENGTH_SHORT).show()
                            }
                            null -> {
                                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            is LoadState.Error -> {
                Toast.makeText(context, "LoadState Error", Toast.LENGTH_SHORT).show()
            }
        }

        /*LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(25) {
                FinishedTestTile(
                    finishedTest = TestState.FinishedTest(
                        finishedTest = FinishedTest(
                            icon = TestIcons.ESSAY,
                            title = "Title name example",
                            description = "Write an essay on any topic. Your English level will be estimated based on it.",
                            level = "A2",
                            levelColor = TestLevelColors.A2,
                        ),
                    ),
                    onClick = {},
                )
                *//*LoadingTestTile(
                    loadingTest = TestState.LoadingTest(
                        loadingTest = LoadingTest(
                            icon = TestIcons.ESSAY,
                            title = "Title name example",
                            description = "Write an essay on any topic. Your English level will be estimated based on it.",
                            progress = 59,
                        ),
                    ),
                    onClick = {},
                )*//*
            }
        }*/
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
            )
        }
    }
}