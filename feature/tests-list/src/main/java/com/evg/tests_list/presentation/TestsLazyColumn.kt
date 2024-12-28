package com.evg.tests_list.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.model.TestIcons
import com.evg.model.TestLevelColors
import com.evg.tests_list.domain.model.FinishedTest
import com.evg.tests_list.domain.model.LoadingTest
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.presentation.model.TestState
import com.evg.tests_list.presentation.mvi.TestsListState
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.flowOf

@Composable
fun TestsLazyColumn(
    tests: LazyPagingItems<ServerResult<TestType, NetworkError>>,
    getAllTests: () -> Unit,
) {
    val context = LocalContext.current
    val refreshingState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize(),
        state = refreshingState,
        onRefresh = { getAllTests() },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                backgroundColor = AppTheme.colors.background,
                contentColor = AppTheme.colors.primary,
            )
        },
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
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TestsLazyColumnPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            TestsLazyColumn(
                tests = flowOf(
                    PagingData.from(
                        listOf<ServerResult<TestType, NetworkError>>(
                            ServerResult.Success(
                                TestType.OnReadyTestType(
                                    id = 1,
                                    title = "Test 1",
                                    type = "Type A",
                                    status = "Ready",
                                    level = "Easy",
                                    createdAt = 1234567890
                                )
                            ),
                            ServerResult.Success(
                                TestType.OnLoadingTestType(
                                    id = 2,
                                    progress = 50,
                                    queue = 2,
                                    createdAt = 1234567891
                                )
                            ),
                            /*ServerResult.Error<TestType, NetworkError>(
                                error = NetworkError.UNKNOWN
                            )*/
                        )
                    )
                ).collectAsLazyPagingItems(),
                getAllTests = {},
            )
        }
    }
}