package com.evg.tests_list.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class TestType : Parcelable {
    @Parcelize
    data class OnReadyTestType(
        val id: Int,
        val title: String,
        val type: String,
        val description: String,
        val level: String,
    ) : TestType()
    @Parcelize
    data class OnLoadingTestType(
        val id: Int,
        val type: String,
        val queue: Int,
        val progress: Int,
    ) : TestType()
    @Parcelize
    data class OnErrorTestType(
        val id: Int,
    ) : TestType()
}
