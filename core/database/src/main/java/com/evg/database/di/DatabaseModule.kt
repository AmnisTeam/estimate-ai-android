package com.evg.database.di

import com.evg.database.data.TestPageSourceLocal
import com.evg.database.data.repository.DatabaseRepositoryImpl
import com.evg.database.domain.model.ErrorTestTypeDBO
import com.evg.database.domain.model.LoadingTestTypeDBO
import com.evg.database.domain.model.ReadyTestTypeDBO
import com.evg.database.domain.model.TestTypeDBO
import com.evg.database.domain.repository.DatabaseRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val databaseModule = module {
    single<Realm> {
        val config = RealmConfiguration.Builder(schema = setOf(
            TestTypeDBO::class,
            ReadyTestTypeDBO::class,
            LoadingTestTypeDBO::class,
            ErrorTestTypeDBO::class
        )).build()
        Realm.open(config)
    }

    single<DatabaseRepository> { DatabaseRepositoryImpl(realm = get()) }
    factory { TestPageSourceLocal(databaseRepository = get()) }
}