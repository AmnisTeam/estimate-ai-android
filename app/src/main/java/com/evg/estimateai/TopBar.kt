package com.evg.estimateai

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.evg.resource.R
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme


val topNavPadding = 56.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navigation: NavController,
) {
    val navBackStackEntry by navigation.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var lastMatchedTitle by remember { mutableStateOf<TopBarTitle?>(null) }
    val (topBarVisible, matchedTitle) = remember(currentDestination) {
        val match = TopBarTitle.allTitles.firstOrNull { topBarTitle ->
            currentDestination?.hasRoute(topBarTitle.route::class) == true
        }

        if (match != null) {
            lastMatchedTitle = match
        }

        (match != null) to lastMatchedTitle
    }

    AnimatedVisibility(
        visible = topBarVisible,
        modifier = Modifier.fillMaxWidth(),
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
    ) {
        Column {
            TopAppBar(
                expandedHeight = topNavPadding - 1.dp,
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(30.dp)
                            .clickableRipple {
                                navigation.popBackStack()
                            },
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = null,
                        tint = AppTheme.colors.text,
                    )
                },
                title = {
                    val title = if (matchedTitle?.title != null) {
                        stringResource(matchedTitle.title)
                    } else { "" }
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = title,
                        style = AppTheme.typography.heading,
                        color = AppTheme.colors.text,
                        textAlign = TextAlign.Center,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = AppTheme.colors.background,
                    titleContentColor = AppTheme.colors.text,
                )
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = AppTheme.colors.bottomBarSelected,
            )

        }
    }
}


@Composable
@Preview(showBackground = true)
fun TopBarPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            TopBar(
                navigation = NavHostController(LocalContext.current),
            )
        }
    }
}