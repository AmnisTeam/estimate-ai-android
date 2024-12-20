package com.evg.api.domain.utils

enum class NetworkError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NOT_FOUND,
    SERVER_ERROR,
    //SERIALIZATION,
    PROTOCOL_EXCEPTION,
    CONNECT_EXCEPTION,
    UNKNOWN,
}