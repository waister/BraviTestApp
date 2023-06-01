package br.com.waister.bravitestapp.features.history

import androidx.lifecycle.ViewModel
import br.com.waister.bravitestapp.data.local.ActivityItem
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.query.Sort

class HistoryViewModel : ViewModel() {

    private val realm: Realm by lazy {
        Realm.open(RealmConfiguration.create(schema = setOf(ActivityItem::class)))
    }

    fun getActivitiesHistory(): RealmResults<ActivityItem> {
        return realm.query<ActivityItem>()
            .sort("startMillis", Sort.DESCENDING)
            .find()
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

}