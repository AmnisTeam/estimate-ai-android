package com.evg.estimateai

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme

@Composable
fun BottomBar(
    navigation: NavHostController
) {
    val navBackStackEntry by navigation.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = AppTheme.colors.bottomBarSelected,
        )
        NavigationBar(
            containerColor = AppTheme.colors.background,
        ) {
            BottomBarScreen.allScreens.forEach { screen ->
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors().copy(
                        selectedIndicatorColor = AppTheme.colors.bottomBarSelected,
                    ),
                    label = {
                        Text(
                            text = screen.title,
                            color = AppTheme.colors.text,
                        )
                    },
                    selected = currentDestination?.hierarchy?.any{
                        it.route == screen.route
                    } == true,
                    onClick = {
                        navigation.navigate(screen.route)
                    },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.title,
                            tint = AppTheme.colors.text,
                        )
                    }
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun BottomBarPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            BottomBar(
                navigation = NavHostController(LocalContext.current),
            )
        }
    }
}