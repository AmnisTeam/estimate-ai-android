package com.evg.tests_list.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.model.TestIcons
import com.evg.model.TestLevelColors
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.presentation.model.TestState
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.flowOf

@Composable
fun TestsLazyColumn(
    tests: LazyPagingItems<ServerResult<TestState, NetworkError>>,
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
                                when (val data = item.data) {
                                    is TestState.LoadingTest -> {
                                        LoadingTestTile(
                                            loadingTest = data,
                                            onClick = {

                                            },
                                        )
                                    }

                                    is TestState.FinishedTest -> {
                                        FinishedTestTile(
                                            finishedTest = data,
                                            onClick = {

                                            },
                                        )
                                    }

                                    is TestState.ErrorTest -> TODO()
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
                        listOf<ServerResult<TestState, NetworkError>>(
                            ServerResult.Success(
                                TestState.FinishedTest(
                                    id = 1,
                                    icon = TestIcons.ESSAY,
                                    title = "Test 1",
                                    description = "qweqweqweqweqweqwe",
                                    levelColor = TestLevelColors.A2,
                                )
                            ),
                            ServerResult.Success(
                                TestState.LoadingTest(
                                    id = 2,
                                    icon = TestIcons.ESSAY,
                                    queue = 1,
                                    progress = 40,
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