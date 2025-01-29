package com.evg.estimateai.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.evg.estimateai.scaffold.EstimateAiScaffold
import com.evg.login.presentation.LoginRoot
import com.evg.password_reset.presentation.PasswordResetRoot
import com.evg.registration.presentation.RegistrationRoot


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation<Route.Authentication>(
        startDestination = Route.Login,
    ) {
        composable<Route.Login> {
            EstimateAiScaffold { paddingValues ->
                LoginRoot(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    onTestsListScreen = {
                        navController.navigate(route = Route.Home) {
                            popUpTo<Route.Authentication> {
                                inclusive = true
                            }
                        }
                    },
                    onPasswordResetScreen = {
                        navController.navigateWithPopAndRestore(
                            targetRoute = Route.PasswordReset,
                            popUpToRoute = Route.Login,
                        )
                    },
                    onRegistrationScreen = {
                        navController.navigateWithPopAndRestore(
                            targetRoute = Route.Registration,
                            popUpToRoute = Route.Login,
                        )
                    },
                )
            }
        }
        composable<Route.Registration> {
            EstimateAiScaffold { paddingValues ->
                RegistrationRoot(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    onLoginScreen = {
                        navController.navigateWithPopAndRestore(
                            targetRoute = Route.Login,
                            popUpToRoute = Route.Registration,
                        )
                    },
                )
            }
        }
        composable<Route.PasswordReset> {
            EstimateAiScaffold { paddingValues ->
                PasswordResetRoot(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    onLoginScreen = {
                        navController.navigateWithPopAndRestore(
                            targetRoute = Route.Login,
                            popUpToRoute = Route.PasswordReset,
                        )
                    },
                )
            }
        }
    }
}

private fun NavController.navigateWithPopAndRestore(
    targetRoute: Route,
    popUpToRoute: Route,
) {
    this.navigate(targetRoute) {
        popUpTo(popUpToRoute) {
            this.inclusive = true
            this.saveState = true
        }
        this.restoreState = true
    }
}
