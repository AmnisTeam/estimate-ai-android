package com.evg.api.di

import com.apollographql.apollo.ApolloClient
import com.evg.api.data.repository.ApiRepositoryImpl
import com.evg.api.domain.repository.ApiRepository
import org.koin.dsl.module

val apiModule = module {
    single {
        ApolloClient.Builder()
            .serverUrl("https://example")
            .build()
    }

    single<ApiRepository> { ApiRepositoryImpl(apolloClient = get()) }
}