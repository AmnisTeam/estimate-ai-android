package com.evg.utils.mapper

import android.content.Context
import com.evg.api.domain.utils.NetworkError
import com.evg.resource.R
import com.evg.utils.model.TestIcons

fun NetworkError.toErrorMessage(context: Context): String {
    return when (this) {
        NetworkError.REQUEST_TIMEOUT -> context.getString(R.string.request_timeout)
        NetworkError.TOO_MANY_REQUESTS -> context.getString(R.string.too_many_requests)
        NetworkError.NOT_FOUND -> context.getString(R.string.not_found)
        NetworkError.SERVER_ERROR -> context.getString(R.string.server_error)
        NetworkError.PROTOCOL_EXCEPTION -> context.getString(R.string.protocol_error)
        NetworkError.CONNECT_EXCEPTION -> context.getString(R.string.connect_error)
        NetworkError.UNKNOWN -> context.getString(R.string.unknown_error)
    }
}

fun String.toTestIcons(): TestIcons {
    return when (this) {
        "essay" -> TestIcons.ESSAY
        else -> TestIcons.UNKNOWN
    }
}