package com.evg.tests_list.presentation

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.model.TestIcons
import com.evg.model.TestLevelColors
import com.evg.resource.R
import com.evg.tests_list.presentation.model.TestState
import com.evg.ui.mapper.toErrorMessage
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.VerticalPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.flowOf

@Composable
fun TestsLazyColumn(
    navigation: NavHostController,
    tests: LazyPagingItems<ServerResult<TestState, NetworkError>>,
    getAllTests: () -> Unit,
    isTestsLoading: Boolean,
) {
    val context = LocalContext.current
    val refreshingState = rememberSwipeRefreshState(isRefreshing = false)
    var swipeEnabled by rememberSaveable { mutableStateOf(true) }

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
                    verticalArrangement = Arrangement.spacedBy(10.dp)
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
                                                when (data.icon) {
                                                    TestIcons.ESSAY -> {
                                                        navigation.navigate("test-essay/${data.id}")
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
                                Toast.makeText(context, item.error.toErrorMessage(context), Toast.LENGTH_SHORT).show()
                            }

                            null -> { }
                        }
                    }
                }
            }

            is LoadState.Error -> {
                swipeEnabled = true

                Toast.makeText(context, stringResource(id = R.string.server_error), Toast.LENGTH_SHORT).show()
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
                navigation = NavHostController(LocalContext.current),
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
                isTestsLoading = false,
            )
        }
    }
}