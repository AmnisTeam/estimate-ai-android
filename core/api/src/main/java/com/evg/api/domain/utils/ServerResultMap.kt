package com.evg.api.domain.utils

inline fun <D, E : RootError, R> ServerResult<D, E>.mapData(transform: (D) -> R): ServerResult<R, E> {
    return when (this) {
        is ServerResult.Success -> ServerResult.Success(transform(this.data))
        is ServerResult.Error -> ServerResult.Error(this.error)
    }
}