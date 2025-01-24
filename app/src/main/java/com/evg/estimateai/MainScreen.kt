package com.evg.estimateai

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.evg.LocalNavHostController
import com.evg.login.presentation.LoginRoot
import com.evg.password_reset.presentation.PasswordResetRoot
import com.evg.registration.presentation.RegistrationRoot
import com.evg.statistics.presentation.StatisticsRoot
import com.evg.test_essay.presentation.TestEssayRoot
import com.evg.test_select.presentation.TestSelectRoot
import com.evg.tests_list.presentation.TestsListRoot
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    // val sharedPreferencesRepository = SharedPrefsRepositoryImpl(context = LocalContext.current)
    // isUserAuthenticated: Boolean = sharedPreferencesRepository.getUserToken() != null
    val layoutDirection = LocalLayoutDirection.current

    /*val startDestination = if (isUserAuthenticated) {
        "product_list"
    } else {
        "registration"
    }*/
    val startDestination = "login"
    val bottomBar = @Composable { BottomBar(navController) }

    CompositionLocalProvider(LocalNavHostController provides navController) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = AppTheme.colors.background,
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(paddingValues)
            ) {
                // Without BottomBar
                composable("registration") { RegistrationRoot() }
                composable("login") { LoginRoot() }
                composable( "password-reset") { PasswordResetRoot() }

                composable( "test-select") { TestSelectRoot() }
                composable(
                    route = "test-essay/{id}",
                    arguments = listOf(
                        navArgument("id") {
                            type = NavType.StringType
                            nullable = true
                            defaultValue = null
                        }
                    ),
                    deepLinks = listOf(navDeepLink { uriPattern = "app://test-essay/{id}" })
                ) { entry ->
                    val id = entry.arguments?.getString("id")?.toIntOrNull()

                    TestEssayRoot(
                        viewModel = koinViewModel(parameters = { parametersOf(id) })
                    )
                }

                // With BottomBar
                BottomBarScreen.allScreens.forEach { screen ->
                    composable(route = screen.route) {
                        when (screen) {
                            is BottomBarScreen.Statistics -> StatisticsRoot(bottomBar = bottomBar)
                            is BottomBarScreen.Tests -> TestsListRoot(bottomBar = bottomBar)
                            is BottomBarScreen.Account -> Column(modifier = Modifier.fillMaxSize()) {
                                Spacer(Modifier.weight(1f))
                                bottomBar()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun MainScreenPreview() {
    EstimateAITheme {
        MainScreen()
    }
}