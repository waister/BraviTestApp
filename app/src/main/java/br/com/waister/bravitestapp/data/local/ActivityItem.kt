package br.com.waister.bravitestapp.data.local

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ActivityItem() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var activityKey: String = ""
    var activityTitle: String = ""
    var status: Int = 0
    var startMillis: Long = 0
    var endMillis: Long = 0
}
