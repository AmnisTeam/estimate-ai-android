package com.evg.api.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.evg.api.data.TestPageSourceRemote
import com.evg.api.data.repository.ApiRepositoryImpl
import com.evg.api.domain.repository.ApiRepository
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val apiModule = module {
    factory {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .build()

        ApolloClient.Builder()
            .serverUrl("http://192.168.1.157:8000/graphql")
            .webSocketServerUrl("ws://192.168.1.157:8000/graphql")
            .okHttpClient(okHttpClient)
            .webSocketReopenWhen { throwable, attempt ->
                println("webSocketReopenWhen №${attempt}")
                println(throwable.cause)
                println(throwable.message)
                delay(2000)
                true
            }
            .webSocketIdleTimeoutMillis(6000)
            .build()
    }

    single<ApiRepository> {
        ApiRepositoryImpl(
            context = get(),
            apolloClientProvider = { get() },
            databaseRepository = get(),
        )
    }
    single { TestPageSourceRemote(apiRepository = get()) }
}