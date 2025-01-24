package org.wm.explore

import com.etg.core.di.coreModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

/**
 * Main Koin module for the app
 */
val appModule = module {
    viewModel {
        CountriesViewModel(get())
    }
    single<CoroutineContext> {
        // Default app coroutine context with SupervisorJob
        Dispatchers.Default + SupervisorJob()
    }
    single<CoroutineScope> {
        // Background app scope on default context
        CoroutineScope(get())
    }
    includes(coreModule)
}