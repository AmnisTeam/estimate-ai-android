package com.evg.test_select.presentation

import androidx.compose.runtime.Composable
import com.evg.LocalNavHostController

@Composable
fun TestSelectRoot() {
    val navigation = LocalNavHostController.current

    TestSelectScreen(
        navigation = navigation,
    )
}