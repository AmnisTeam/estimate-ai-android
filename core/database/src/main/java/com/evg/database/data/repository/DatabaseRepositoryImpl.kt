package com.evg.database.data.repository

import com.evg.database.domain.model.TestDataDBO
import com.evg.database.domain.model.TestTypeDBO
import com.evg.database.domain.repository.DatabaseRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseRepositoryImpl(
    private val realm: Realm
): DatabaseRepository {
    override fun getAllTests(): List<TestTypeDBO> {
        return realm
            .query<TestTypeDBO>()
            .find()
            .toList()
    }

    override suspend fun addTests(tests: List<TestTypeDBO>) {
        realm.write {
            tests.forEach { test ->
                copyToRealm(test, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    override suspend fun updateTests(tests: List<TestTypeDBO>) {
        realm.write {
            tests.forEach { test ->
                val existingTest = query<TestTypeDBO>("id == $0", test.id).first().find()

                if (existingTest != null) {
                    copyToRealm(test, updatePolicy = UpdatePolicy.ALL)
                }
            }
        }
    }

    override suspend fun deleteTestById(id: Int) {
        realm.write {
            val testToDelete = query<TestTypeDBO>("id == $0", id).first().find()
            if (testToDelete != null) {
                delete(testToDelete)
            }
        }
    }

    override suspend fun addTestData(data: TestDataDBO) {
        realm.write {
            copyToRealm(data, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override fun getTestData(id: Int): TestDataDBO? {
        return realm
            .query<TestDataDBO>("id == $0", id)
            .first()
            .find()
    }
}