package com.evg.api.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.annotations.ApolloExperimental
import com.apollographql.apollo.network.okHttpClient
import com.evg.api.data.TestPageSourceRemote
import com.evg.api.data.repository.ApiRepositoryImpl
import com.evg.api.domain.repository.ApiRepository
import com.evg.shared_prefs.domain.repository.SharedPrefsRepository
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val apiModule = module {
    factory {
        val timeout: Long = 2_000

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .writeTimeout(timeout, TimeUnit.MILLISECONDS)
            .addInterceptor { chain ->
                val token = get<SharedPrefsRepository>().getUserToken()
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                chain.proceed(newRequest)
            }
            .build()

        ApolloClient.Builder()
            .serverUrl("http://192.168.1.157:8000/graphql")
            .webSocketServerUrl("ws://192.168.1.157:8000/graphql")
            .okHttpClient(okHttpClient)
            .webSocketIdleTimeoutMillis(timeout)
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