package com.evg.estimateai.graphs

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.evg.estimateai.bottomNavPadding
import com.evg.estimateai.scaffold.EstimateAiScaffold
import com.evg.statistics.presentation.StatisticsRoot
import com.evg.tests_list.presentation.TestsListRoot


@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    animatedVisibilityScope: SharedTransitionScope,
) {
    navigation<Route.Home>(
        startDestination = Route.TestsList,
    ) {
        composable<Route.TestsList> {
            EstimateAiScaffold(
                modifier = Modifier.padding(bottom = bottomNavPadding)
            ) { paddingValues ->
                animatedVisibilityScope.TestsListRoot(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    animatedVisibilityScope = this,
                    onTestSelectScreen = {
                        navController.navigate(route = Route.TestCreation)
                    },
                    onTestEssayScreen = { id, score -> navController.navigate(route = Route.TestEssay(id = id, score = score)) },
                )
            }
        }
        composable<Route.Statistics> {
            EstimateAiScaffold(
                modifier = Modifier.padding(bottom = bottomNavPadding),
            ) { paddingValues ->
                StatisticsRoot(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                )
            }
        }
        composable<Route.Account> { }

        createTestNavGraph(
            navController = navController,
            animatedVisibilityScope = animatedVisibilityScope,
        )
    }
}
