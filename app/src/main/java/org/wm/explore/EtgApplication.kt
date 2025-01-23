package org.wm.explore

import android.app.Application
import com.etg.core.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EtgApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EtgApplication)
            modules(appModule + coreModule)
        }
    }
}