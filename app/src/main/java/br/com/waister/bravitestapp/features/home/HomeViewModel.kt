package br.com.waister.bravitestapp.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.waister.bravitestapp.BuildConfig
import br.com.waister.bravitestapp.data.remote.ActivityService
import br.com.waister.bravitestapp.models.ActivityResponse
import br.com.waister.bravitestapp.utils.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class HomeViewModel(private val activityService: ActivityService) : ViewModel() {

    private val _activity = MutableLiveData<ViewState<ActivityResponse>>()
    val activity: LiveData<ViewState<ActivityResponse>> get() = _activity

    private val activitiesExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (BuildConfig.DEBUG) throwable.printStackTrace()
        _activity.value = ViewState.Error(Throwable("Ocorreu um erro desconhecido, tente novamente"))
    }

    init {
        getAnActivity()
    }

    fun getAnActivity() {
        viewModelScope.launch(activitiesExceptionHandler) {
            _activity.value = ViewState.Loading

            val response = activityService.getRandom(type = "recreational")

            _activity.value = if (response.isSuccessful && response.body() != null)
                ViewState.Success(response.body()!!)
            else
                ViewState.Error(Throwable())
        }
    }
}