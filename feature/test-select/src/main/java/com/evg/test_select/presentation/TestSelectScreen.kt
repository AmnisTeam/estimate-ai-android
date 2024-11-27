package com.evg.test_select.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.evg.model.TestIcons
import com.evg.resource.R
import com.evg.test_select.presentation.model.TestType
import com.evg.ui.custom.Header
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPaddingTile
import com.evg.ui.theme.VerticalPadding

@Composable
fun TestSelectScreen(
    navigation: NavHostController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Header(
            navigation = navigation,
            title = stringResource(id = R.string.select_test_type)
        )
        
        Column(
            modifier = Modifier
                .padding(
                    horizontal = HorizontalPaddingTile,
                    vertical = VerticalPadding,
                ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TestTypeTile(
                testType = TestType(
                    icon = TestIcons.ESSAY,
                    title = "The essay test",
                    description = "Write an essay on any topic. Your English level will be estimated based on it",
                ),
                onClick = {
                    navigation.navigate("test-essay")
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TestSelectScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            TestSelectScreen(
                navigation = NavHostController(LocalContext.current),
            )
        }
    }
}