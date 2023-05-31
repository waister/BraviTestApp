package br.com.waister.bravitestapp.di

import br.com.waister.bravitestapp.data.remote.ActivityService
import br.com.waister.bravitestapp.data.remote.RemoteConfig
import org.koin.dsl.module

val serviceModule = module {

    single { RemoteConfig(ActivityService::class.java, endPoint = "/").getApiService() }

}
