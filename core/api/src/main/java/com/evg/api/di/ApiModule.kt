package com.evg.api.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.evg.api.data.TestPageSourceRemote
import com.evg.api.data.repository.ApiRepositoryImpl
import com.evg.api.domain.repository.ApiRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val apiModule = module {
    single {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .build()

        ApolloClient.Builder()
            .serverUrl("http://192.168.1.157:8000/graphql")
            .webSocketServerUrl("ws://192.168.1.157:8000/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }

    single<ApiRepository> { ApiRepositoryImpl(
        context = get(),
        apolloClient = get(),
        databaseRepository = get(),
    ) }
    single { TestPageSourceRemote(apiRepository = get()) }
}