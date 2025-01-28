package com.evg.estimateai.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.evg.estimateai.scaffold.EstimateAiScaffold
import com.evg.estimateai.topNavPadding
import com.evg.test_essay.presentation.TestEssayRoot
import com.evg.test_select.presentation.TestSelectRoot
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.createTestNavGraph(navController: NavHostController) {
    navigation<Route.TestCreation>(
        startDestination = Route.TestSelect,
    ) {
        composable<Route.TestSelect> {
            EstimateAiScaffold(
                modifier = Modifier.padding(top = topNavPadding),
            ) { paddingValues ->
                TestSelectRoot(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    onTestEssayScreen = { navController.navigate(route = Route.TestEssay(id = null)) },
                )
            }
        }
        composable<Route.TestEssay>(
            deepLinks = listOf(navDeepLink { uriPattern = "app://test-essay/{id}" })
        ) { entry ->
            val id = entry.toRoute<Route.TestEssay>().id
            EstimateAiScaffold(
                modifier = Modifier.padding(top = topNavPadding),
            ) { paddingValues ->
                TestEssayRoot(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    viewModel = koinViewModel(parameters = { parametersOf(id) }),
                    onTestsListScreen = { navController.navigate(route = Route.Home) },
                )
            }
        }
    }
}
