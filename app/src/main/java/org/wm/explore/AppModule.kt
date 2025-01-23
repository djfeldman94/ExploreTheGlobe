package org.wm.explore

import com.etg.core.di.coreModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        CountriesViewModel(get())
    }
    includes(coreModule)
}