package com.evg.database.domain.model

import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class TestDataDBO : RealmObject {
    @PrimaryKey var id: Int = 0
    var essayTestDBO: EssayTestDBO? = null
}

open class EssayTestDBO : EmbeddedRealmObject {
    var essay: String = ""
}
