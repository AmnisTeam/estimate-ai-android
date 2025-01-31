package com.evg.estimateai.graphs

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.evg.estimateai.scaffold.EstimateAiScaffold
import com.evg.estimateai.topNavPadding
import com.evg.test_essay.presentation.TestEssayRoot
import com.evg.test_select.presentation.TestSelectRoot
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.createTestNavGraph(
    navController: NavHostController,
    animatedVisibilityScope: SharedTransitionScope,
) {
    composable<Route.TestSelect> {
        EstimateAiScaffold(
            modifier = Modifier.padding(top = topNavPadding),
        ) { paddingValues ->
            animatedVisibilityScope.TestSelectRoot(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                animatedVisibilityScope = this,
                onTestEssayScreen = {
                    navController.navigate(route = Route.TestEssay(id = null, score = null))
                },
            )
        }
    }
    composable<Route.TestEssay>(
        deepLinks = listOf(navDeepLink { uriPattern = "app://test-essay/{id}/{score}" })
    ) { entry ->
        val testEssay = entry.toRoute<Route.TestEssay>()
        EstimateAiScaffold(
            modifier = Modifier.padding(top = topNavPadding),
        ) { paddingValues ->
            TestEssayRoot(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                viewModel = koinViewModel(parameters = { parametersOf(testEssay.id) }),
                onTestsListScreen = {
                    navController.navigate(route = Route.Home) {
                        popUpTo<Route.Home> {
                            inclusive = true
                        }
                    }
                },
                score = testEssay.score,
            )
        }
    }
}
