package com.evg.estimateai.graphs

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable data object Authentication: Route
        @Serializable data object Login: Route
        @Serializable data object Registration: Route
        @Serializable data object PasswordReset: Route

    @Serializable data object Home: Route
        @Serializable data object TestsList: Route
        @Serializable data object Statistics: Route
        @Serializable data object Account: Route

        @Serializable data object TestSelect: Route
        @Serializable data class TestEssay(val id: Int?, val score: Int?): Route
}
