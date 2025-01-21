package com.evg.database.domain.repository

import com.evg.database.domain.model.ReadyTestTypeDBO
import com.evg.database.domain.model.TestDataDBO
import com.evg.database.domain.model.TestTypeDBO

interface DatabaseRepository {
    fun getAllTests(/*pageSize: Int, offset: Int*/): List<TestTypeDBO>
    fun getTestStatistics(): List<TestTypeDBO>
    suspend fun addTests(tests: List<TestTypeDBO>)
    suspend fun updateTests(tests: List<TestTypeDBO>)
    suspend fun deleteTestById(id: Int)

    suspend fun addTestData(data: TestDataDBO)
    fun getTestData(id: Int): TestDataDBO?
}