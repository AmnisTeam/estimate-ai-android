package com.evg.database.domain.model

import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class TestTypeDBO : RealmObject {
    @PrimaryKey var id: Int = 0
    var readyTestTypeDBO: ReadyTestTypeDBO? = null
    var loadingTestTypeDBO: LoadingTestTypeDBO? = null
    var errorTestTypeDBO: ErrorTestTypeDBO? = null
}

open class ReadyTestTypeDBO : EmbeddedRealmObject {
    var title: String = ""
    var type: String = ""
    var description: String = ""
    var score: Int = 0
    var createdAt: Long = 0
}

open class LoadingTestTypeDBO : EmbeddedRealmObject {
    var type: String = ""
    var queue: Int = 0
    var progress: Int = 0
    var createdAt: Long = 0
}

open class ErrorTestTypeDBO : EmbeddedRealmObject {
    var createdAt: Long = 0
}
