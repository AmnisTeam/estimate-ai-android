package com.evg.estimateai

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.evg.estimateai.graphs.Route
import com.evg.estimateai.graphs.authNavGraph
import com.evg.estimateai.graphs.homeNavGraph
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme

val bottomNavPadding = 81.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    // val sharedPreferencesRepository = SharedPrefsRepositoryImpl(context = LocalContext.current)
    // isUserAuthenticated: Boolean = sharedPreferencesRepository.getUserToken() != null
    //val layoutDirection = LocalLayoutDirection.current

    /*val startDestination = if (isUserAuthenticated) {
        "product_list"
    } else {
        "registration"
    }*/
    val navController = rememberNavController()
    val startDestination = Route.Authentication


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController) },
        containerColor = AppTheme.colors.background,
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.background(AppTheme.colors.background),
        ) {
            authNavGraph(navController = navController)

            homeNavGraph(navController = navController)
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