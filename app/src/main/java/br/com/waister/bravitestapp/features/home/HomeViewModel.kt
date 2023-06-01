package br.com.waister.bravitestapp.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.waister.bravitestapp.BuildConfig
import br.com.waister.bravitestapp.data.local.ActivityItem
import br.com.waister.bravitestapp.data.remote.ActivityService
import br.com.waister.bravitestapp.models.ActivityResponse
import br.com.waister.bravitestapp.utils.STATUS_STARTED
import br.com.waister.bravitestapp.utils.ViewState
import br.com.waister.bravitestapp.utils.successValue
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class HomeViewModel(private val activityService: ActivityService) : ViewModel() {

    private val _activity = MutableLiveData<ViewState<ActivityResponse>>()
    val activity: LiveData<ViewState<ActivityResponse>> get() = _activity

    private val activitiesExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (BuildConfig.DEBUG) throwable.printStackTrace()
        _activity.value = ViewState.Error(Throwable("Ocorreu um erro desconhecido, tente novamente"))
    }

    private val realm: Realm by lazy {
        Realm.open(RealmConfiguration.create(schema = setOf(ActivityItem::class)))
    }

    var typeSelected: String = ""
    var statedActivity: ActivityItem? = null

    init {
        checkStartedActivity()
    }

    private fun checkStartedActivity() {
        val items = realm.query<ActivityItem>("status == \$0", STATUS_STARTED).find()
        statedActivity = if (items.isNotEmpty()) items.first() else null

        if (statedActivity != null)
            _activity.value = ViewState.Success(
                ActivityResponse(activity = statedActivity!!.activityTitle, key = statedActivity!!.activityKey)
            )
        else
            getNewActivity()
    }

    fun getNewActivity() {
        statedActivity = null

        viewModelScope.launch(activitiesExceptionHandler) {
            _activity.value = ViewState.Loading

            val response = activityService.getRandom(type = "recreational")

            _activity.value = if (response.isSuccessful && response.body() != null)
                ViewState.Success(response.body()!!)
            else
                ViewState.Error(Throwable())
        }
    }

    fun startActivity(statusToSet: Int) {
        val currentActivity = _activity.value!!.successValue!!

        realm.writeBlocking {
            copyToRealm(ActivityItem().apply {
                activityKey = currentActivity.key
                activityTitle = currentActivity.activity
                status = statusToSet
                startMillis = System.currentTimeMillis()
            })
        }
    }

    fun updateActivity(statusToSet: Int) {
        val currentActivity = _activity.value!!.successValue!!
        val items = realm.query<ActivityItem>("activityKey == \$0", currentActivity.key).find()

        if (items.isNotEmpty()) {
            realm.writeBlocking {
                val item = findLatest(items[0])
                item?.status = statusToSet
                item?.endMillis = System.currentTimeMillis()
            }
        }
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

}