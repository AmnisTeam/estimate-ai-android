package com.evg.api.di

import com.apollographql.apollo.ApolloClient
import com.evg.api.data.TestPageSourceRemote
import com.evg.api.data.repository.ApiRepositoryImpl
import com.evg.api.domain.repository.ApiRepository
import org.koin.dsl.module

val apiModule = module {
    single {
        ApolloClient.Builder()
            .serverUrl("http://192.168.1.157:8000/graphql")
            .webSocketServerUrl("ws://192.168.1.157:8000/graphql")
            .build()
    }

    single<ApiRepository> { ApiRepositoryImpl(
        context = get(),
        apolloClient = get()
    ) }
    factory { TestPageSourceRemote(apiRepository = get()) }
}