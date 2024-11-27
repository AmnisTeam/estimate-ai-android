package com.evg.estimateai

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.evg.registration.presentation.RegistrationRoot
import com.evg.LocalNavHostController
import com.evg.login.presentation.LoginRoot
import com.evg.password_reset.presentation.PasswordResetRoot
import com.evg.tests_list.presentation.TestsListRoot
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme

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

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    CompositionLocalProvider(LocalNavHostController provides navController) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = AppTheme.colors.background,
            bottomBar = {
                AnimatedVisibility(
                    visible = currentDestination in BottomBarScreen.allRoutes,
                    enter = fadeIn() + slideInVertically { it },
                    exit = fadeOut() + slideOutVertically { it },
                ) {
                    BottomBar(navController)
                }
            }
        ) { paddingValues ->
            /*Box(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        start = paddingValues.calculateStartPadding(layoutDirection),
                        end = paddingValues.calculateEndPadding(layoutDirection)
                    )
            ) {*/
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    // Без BottomBar
                    composable("registration") { RegistrationRoot() }
                    composable("login") { LoginRoot() }
                    composable( "password_reset") { PasswordResetRoot() }

                    // С BottomBar
                    composable(route = BottomBarScreen.Statistics.route) {
                        //
                    }
                    composable(route = BottomBarScreen.Tests.route) {
                        TestsListRoot()
                    }
                    composable(route = BottomBarScreen.Account.route) {
                        //
                    }
                }
            //}
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