package br.com.waister.bravitestapp

import android.app.Application
import br.com.waister.bravitestapp.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(serviceModule)
        }
    }
}
