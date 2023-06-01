package br.com.waister.bravitestapp.data.local

import io.realm.kotlin.types.RealmObject

class ActivityItem : RealmObject {
    var activityKey: String = ""
    var activityTitle: String = ""
    var status: Int = 0
    var startMillis: Long = 0
    var endMillis: Long = 0
}
