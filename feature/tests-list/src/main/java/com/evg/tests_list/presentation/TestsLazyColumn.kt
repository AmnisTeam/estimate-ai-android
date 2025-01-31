package com.evg.tests_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.resource.R
import com.evg.tests_list.presentation.model.TestState
import com.evg.ui.snackbar.SnackBarController
import com.evg.ui.snackbar.SnackBarEvent
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.VerticalPadding
import com.evg.utils.mapper.toErrorMessage
import com.evg.utils.model.TestIcons
import com.evg.utils.model.TestScore
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.flowOf

@Composable
fun TestsLazyColumn(
    onTestEssayScreen: (id: Int, score: Int) -> Unit,
    tests: LazyPagingItems<ServerResult<TestState, NetworkError>>,
    getAllTests: () -> Unit,
    isTestsLoading: Boolean,
) {
    val context = LocalContext.current
    val refreshingState = rememberSwipeRefreshState(isRefreshing = false)
    var swipeEnabled by rememberSaveable { mutableStateOf(true) }

    val spacedByPadding = 10.dp

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize(),
        state = refreshingState,
        swipeEnabled = swipeEnabled,
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
                swipeEnabled = false

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(spacedByPadding)
                ) {
                    items(10) {
                        ShimmerTestTile()
                    }
                }
            }

            is LoadState.NotLoading -> {
                swipeEnabled = true

                if (tests.itemCount <= 1 && !isTestsLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(100.dp),
                                painter = painterResource(id = R.drawable.sad),
                                contentDescription = null,
                                tint = AppTheme.colors.text,
                            )

                            Spacer(modifier = Modifier.height(VerticalPadding))

                            Text(
                                text = "${stringResource(id = R.string.list_tests_is_empty)}.\n" +
                                        "${stringResource(id = R.string.swipe_to_update)}.",
                                style = AppTheme.typography.body,
                                color = AppTheme.colors.text,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(spacedByPadding)
                ) {
                    items(
                        count = tests.itemCount,
                        key = { it }
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
                                                when (data.icon) {
                                                    TestIcons.ESSAY -> {
                                                        onTestEssayScreen(data.id, data.score.score)
                                                    }
                                                    TestIcons.UNKNOWN -> { }
                                                }
                                            },
                                        )
                                    }

                                    is TestState.ErrorTest -> TODO()
                                }
                            }

                            is ServerResult.Error -> {
                                val errorMessage = item.error.toErrorMessage(context)
                                LaunchedEffect(errorMessage) {
                                    SnackBarController.sendEvent(SnackBarEvent(errorMessage))
                                }
                            }

                            null -> { }
                        }
                    }
                }
            }

            is LoadState.Error -> {
                swipeEnabled = true
                val errorMessage = stringResource(id = R.string.server_error)
                LaunchedEffect(errorMessage) {
                    SnackBarController.sendEvent(SnackBarEvent(errorMessage))
                }
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
                                    score = TestScore(0),
                                    createdAt = 0,
                                )
                            ),
                            ServerResult.Success(
                                TestState.LoadingTest(
                                    id = 2,
                                    icon = TestIcons.ESSAY,
                                    queue = 1,
                                    progress = 40,
                                    createdAt = 0,
                                )
                            ),
                            /*ServerResult.Error<TestType, NetworkError>(
                                error = NetworkError.UNKNOWN
                            )*/
                        )
                    )
                ).collectAsLazyPagingItems(),
                getAllTests = {},
                onTestEssayScreen = { _,_ -> },
                isTestsLoading = false,
            )
        }
    }
}